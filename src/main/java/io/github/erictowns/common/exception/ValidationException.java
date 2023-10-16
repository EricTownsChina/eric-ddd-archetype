package io.github.erictowns.common.exception;

/**
 * desc: 校验异常
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 22:33
 */
public class ValidationException extends BaseException {

    private static final long serialVersionUID = 3030066522545238306L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
