package io.github.erictowns.common.exception;

/**
 * desc: Base Exception
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:19
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -7629606853499219158L;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

}
