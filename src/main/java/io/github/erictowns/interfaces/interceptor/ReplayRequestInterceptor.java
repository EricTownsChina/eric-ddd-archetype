package io.github.erictowns.interfaces.interceptor;

import io.github.erictowns.common.exception.ReplayAttackException;
import io.github.erictowns.common.utils.JasyptUtil;
import io.github.erictowns.interfaces.entity.RespStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: todo
 *
 * @author EricTowns
 * @date 2023/10/18 17:21
 */
@Configuration
public class ReplayRequestInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplayRequestInterceptor.class);

    private static String sign(String token, String timestamp, String nonce) {
        String sign = token + timestamp + nonce;
        return JasyptUtil.stringEncryptor(ReplayRequestInterceptor.class.getName(), sign, true);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String timestamp = request.getHeader("timestamp");
        String nonce = request.getHeader("nonce");
        String signature = request.getHeader("signature");

        if (StringUtils.isEmpty(timestamp)) {
            LOGGER.warn("replay request invalidate error, param timestamp is abnormal, timestamp: {},  url: {}",
                    timestamp, request.getRequestURL());
            throw new ReplayAttackException(RespStatus.SUSPECTED_REPLAY_ATTACK.getMsg());
        } else if (StringUtils.isEmpty(nonce)) {
            LOGGER.warn("replay request invalidate error, param nonce is abnormal, nonce: {},  url: {}",
                    nonce, request.getRequestURL());
            throw new ReplayAttackException(RespStatus.SUSPECTED_REPLAY_ATTACK.getMsg());
        } else if (StringUtils.isEmpty(signature)) {
            LOGGER.warn("replay request invalidate error, param signature is abnormal, signature: {},  url: {}",
                    signature, request.getRequestURL());
            throw new ReplayAttackException(RespStatus.SUSPECTED_REPLAY_ATTACK.getMsg());
        } else {
            return true;
        }
    }
}
