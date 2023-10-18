package io.github.erictowns.common.exception;

/**
 * Description: 重放攻击异常
 *
 * @author EricTowns
 * @date 2023/10/18 21:20
 */
public class ReplayAttackException extends BaseException {

    public ReplayAttackException(String message) {
        super(message);
    }

    public ReplayAttackException(String message, Throwable cause) {
        super(message, cause);
    }
}
