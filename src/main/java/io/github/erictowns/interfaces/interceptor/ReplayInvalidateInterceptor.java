package io.github.erictowns.interfaces.interceptor;

import io.github.erictowns.common.exception.ReplayAttackException;
import io.github.erictowns.common.utils.JasyptUtil;
import io.github.erictowns.domain.user.repository.CacheRepository;
import io.github.erictowns.interfaces.config.ReplayInvalidateConfig;
import io.github.erictowns.interfaces.entity.RespStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 重放攻击检验拦截器
 * {@link ReplayInvalidateConfig.ReplayInvalidateProperties}里面的token需要前后端约定
 *
 * @author EricTowns
 * @date 2023/10/18 17:21
 */
public class ReplayInvalidateInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplayInvalidateInterceptor.class);
    private static final String PREFIX_NONCE = "nonce::";

    private final CacheRepository cacheRepository;
    private final ReplayInvalidateConfig.ReplayInvalidateProperties properties;

    public ReplayInvalidateInterceptor(CacheRepository cacheRepository,
                                       ReplayInvalidateConfig.ReplayInvalidateProperties properties) {
        this.cacheRepository = cacheRepository;
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
