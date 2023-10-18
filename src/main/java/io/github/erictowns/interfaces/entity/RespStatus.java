package io.github.erictowns.interfaces.entity;

/**
 * desc: response status enum
 *
 * @author EricTowns
 * @date 2023-10-01 16:31
 */
public enum RespStatus {

    /**
     * status ok
     */
    OK(200, "ok"),
    /**
     * status error
     */
    ERROR(500, "error"),
    /**
     * suspected replay attack
     */
    SUSPECTED_REPLAY_ATTACK(5100, "suspected replay attack");


    private final int code;
    private final String msg;

    RespStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
