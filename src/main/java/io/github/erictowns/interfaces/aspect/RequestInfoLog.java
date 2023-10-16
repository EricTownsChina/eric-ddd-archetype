package io.github.erictowns.interfaces.aspect;

import io.github.erictowns.common.utils.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: log request info
 * 只在dev环境生效
 *
 * @author EricTowns
 * @date 2023/10/10 20:04
 */
@Aspect
@Component
@Profile("dev")
public class RequestInfoLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInfoLog.class);

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object accountController(ProceedingJoinPoint joinPoint) throws Throwable {
        return doAround(joinPoint);
    }

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        long startTime = System.currentTimeMillis();
        Object result = null;
        String errorMessage = "";
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            errorMessage = throwable.getLocalizedMessage();
            throw throwable;
        } finally {
            // 打印请求 url
            LOGGER.info("\r\n\t\tURL            : {}" +
                            "\r\n\t\tHTTP Method    : {}" +
                            "\r\n\t\tClass Method   : {}.{}" +
                            "\r\n\t\tIP             : {}" +
                            "\r\n\t\tTime-Consuming : {} ms" +
                            "\r\n\t\tRequest Args   : {}" +
                            "\r\n\t\tResponse Data  : {}" +
                            "\r\n\t\tResponse ERROR : {}" +
                            "\r\n",
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    request.getRemoteAddr(),
                    System.currentTimeMillis() - startTime,
                    getParams(joinPoint),
                    JsonUtil.toJson(result),
                    errorMessage
            );
        }
        return result;
    }

    private String getParams(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<>(0);
        // 参数名
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            params = new HashMap<>(args.length);
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if ((arg instanceof HttpServletResponse) || (arg instanceof HttpServletRequest)
                        || (arg instanceof MultipartFile) || (arg instanceof MultipartFile[])) {
                    continue;
                }
                try {
                    params.put(argNames[i], args[i]);
                } catch (Exception e1) {
                    LOGGER.error(e1.getMessage());
                }
            }
        }
        return JsonUtil.toJson(params);
    }
}
