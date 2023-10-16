package io.github.erictowns.common.utils;

import io.github.erictowns.common.exception.ValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * desc: validate util
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:56
 */
public final class ValidationUtil {

    /**
     * 参数校验器
     */
    private static final Validator VALIDATOR = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory()
            .getValidator();

    private ValidationUtil() {
    }

    /**
     * 参数校验
     *
     * @param bean 要校验的对象
     * @param <T>  T
     */
    public static <T> void validate(T bean) {
        if (null == bean) {
            throw new ValidationException("待校验对象不能为空!");
        }
        Set<ConstraintViolation<T>> validateResults = VALIDATOR.validate(bean);
        if (CollectionUtils.isNotEmpty(validateResults)) {
            throw new ValidationException("自定义参数校验异常 : " + validateResults.iterator().next().getMessage());
        }
    }

    /**
     * 分组参数校验
     *
     * @param bean 要校验的对象
     * @param <T>  T
     */
    public static <T> void validate(T bean, Class<?> group) {
        if (null == bean) {
            throw new ValidationException("待校验对象不能为空!");
        }
        Set<ConstraintViolation<T>> validateResults = VALIDATOR.validate(bean, group);
        if (CollectionUtils.isNotEmpty(validateResults)) {
            throw new ValidationException("自定义参数校验异常 : " + validateResults.iterator().next().getMessage());
        }
    }

}
