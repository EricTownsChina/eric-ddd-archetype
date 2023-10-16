package io.github.erictowns.common.exception;

/**
 * desc: json serialization exception
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:21
 */
public class JsonSerializationException extends BaseException {
    private static final long serialVersionUID = -4564243833889626522L;

    public JsonSerializationException() {
    }

    public JsonSerializationException(String message) {
        super(message);
    }

    public JsonSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSerializationException(Throwable cause) {
        super(cause);
    }
}
