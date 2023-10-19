package io.github.erictowns.interfaces.filter;

import io.github.erictowns.common.exception.ReplayAttackException;
import io.github.erictowns.common.utils.JasyptUtil;
import io.github.erictowns.domain.user.repository.CacheRepository;
import io.github.erictowns.interfaces.config.ReplayInvalidateConfig;
import io.github.erictowns.interfaces.entity.RespStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 重放攻击校验过滤器
 * header传参和token以及签名加密算法需要和前端约定
 *
 * @author EricTowns
 * @date 2023/10/19 20:43
 */
public class ReplayInvalidateFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplayInvalidateFilter.class);
    private static final String PREFIX_NONCE = "nonce::";

    private final CacheRepository cacheRepository;
    private final ReplayInvalidateConfig.ReplayInvalidateProperties properties;

    public ReplayInvalidateFilter(CacheRepository cacheRepository,
                                  ReplayInvalidateConfig.ReplayInvalidateProperties properties) {
        this.cacheRepository = cacheRepository;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String timestamp = request.getHeader("timestamp");
        String nonce = request.getHeader("nonce");
        String signature = request.getHeader("signature");
        String token = properties.getToken();
        long expireSeconds = properties.getExpireSeconds();
        long now = System.currentTimeMillis();

        // 缺少时间戳参数/时间戳超过了过期时间
        if (StringUtils.isEmpty(timestamp) || now - Long.parseLong(timestamp) > (expireSeconds * 1000)) {
            LOGGER.warn("replay request invalidate error, param timestamp is abnormal, timestamp: {},  url: {}",
                    timestamp, request.getRequestURL());
            throw new ReplayAttackException(RespStatus.SUSPECTED_REPLAY_ATTACK.getMsg());
        }
        // 缺少随机参数/随机参数已经存在缓存
        if (StringUtils.isEmpty(nonce) || cacheRepository.exists(PREFIX_NONCE + nonce)) {
            LOGGER.warn("replay request invalidate error, param nonce is abnormal, nonce: {},  url: {}",
                    nonce, request.getRequestURL());
            throw new ReplayAttackException(RespStatus.SUSPECTED_REPLAY_ATTACK.getMsg());
        }
        // 缺少签名/签名不正确
        String decryptText = JasyptUtil.stringEncryptor(nonce, signature, false);
        String originText = token + timestamp + nonce;
        if (StringUtils.isEmpty(signature) || !originText.equals(decryptText)) {
            LOGGER.warn("replay request invalidate error, param signature is abnormal, signature: {},  url: {}",
                    signature, request.getRequestURL());
            throw new ReplayAttackException(RespStatus.SUSPECTED_REPLAY_ATTACK.getMsg());
        }

        // nonce缓存
        cacheRepository.set(PREFIX_NONCE + nonce, nonce, expireSeconds);
        filterChain.doFilter(request, response);
    }

}
