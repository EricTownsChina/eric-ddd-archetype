package io.github.erictowns.interfaces.config;

import io.github.erictowns.common.exception.BaseException;
import io.github.erictowns.common.exception.ReplayAttackException;
import io.github.erictowns.common.exception.ValidationException;
import io.github.erictowns.common.utils.ValidationUtil;
import io.github.erictowns.interfaces.entity.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Description: 请求异常统一处理
 *
 * @author EricTowns
 * @date 2023/10/10 20:56
 */
@RestController
@ControllerAdvice
public class RequestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestExceptionHandler.class);

    /**
     * 处理{@link Exception}
     *
     * @param e {@link Exception}
     * @return {@link Resp}
     */
    @ExceptionHandler(value = Exception.class)
    public Resp exceptionHandler(Exception e) {
        LOGGER.error("统一异常处理: ", e);
        return Resp.fail(e.getLocalizedMessage());
    }

    /**
     * 处理{@link BaseException}
     *
     * @param e {@link BaseException}
     * @return {@link Resp}
     */
    @ExceptionHandler(value = BaseException.class)
    public Resp baseExceptionHandler(BaseException e) {
        LOGGER.error("统一异常处理: ", e);
        return Resp.fail(e.getLocalizedMessage());
    }

    /**
     * 处理{@link BindException} <br/>
     * POST请求处理入参时，参数校验异常
     *
     * @param e {@link BindException}
     * @return {@link Resp}
     */
    @ExceptionHandler(BindException.class)
    public Resp handleException(BindException e) {
        LOGGER.error("validator error: ", e);
        String msg = e.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Resp.fail(msg);
    }

    /**
     * 处理{@link ConstraintViolationException} <br/>
     * GET请求处理入参时，参数校验异常
     *
     * @param e {@link ConstraintViolationException}
     * @return {@link Resp}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Resp handleException(ConstraintViolationException e) {
        LOGGER.error("validator error: ", e);
        String msg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return Resp.fail(msg);
    }

    /**
     * 处理{@link ValidationException} <br/>
     * {@link ValidationUtil}中手动校验抛出的异常
     *
     * @param e {@link ValidationException}
     * @return {@link Resp}
     */
    @ExceptionHandler(ValidationException.class)
    public Resp handleException(ValidationException e) {
        LOGGER.error("validator error: ", e);
        String msg = e.getLocalizedMessage();
        return Resp.fail(msg);
    }

    /**
     * 处理{@link ReplayAttackException} <br/>
     * 疑似重放攻击请求
     *
     * @param e {@link ReplayAttackException}
     * @return {@link Resp}
     */
    @ExceptionHandler(ReplayAttackException.class)
    public Resp handleException(ReplayAttackException e) {
        LOGGER.error("replay request invalidate error: ", e);
        String msg = e.getLocalizedMessage();
        return Resp.fail(msg);
    }

}
