package io.github.erictowns.interfaces.entity;

/**
 * desc: web response
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-01 11:46
 */
public class Resp {

    private int code;
    private String msg;
    private Object data;

    public Resp() {
    }

    public Resp(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }

    public static Resp success() {
        return new Builder().setRespStatus(RespStatus.OK).build();
    }

    public static Resp success(Object data) {
        return new Builder().setRespStatus(RespStatus.OK).setData(data).build();
    }

    public static Resp fail() {
        return new Builder().setRespStatus(RespStatus.ERROR).build();
    }

    public static Resp fail(RespStatus respStatus) {
        return new Builder().setRespStatus(respStatus).build();
    }

    public static Resp fail(String msg) {
        return new Builder().setRespStatus(RespStatus.ERROR).setMsg(msg).build();
    }

    public static Resp fail(RespStatus respStatus, String msg) {
        return new Builder().setCode(respStatus.getCode()).setMsg(msg).build();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class Builder {
        private int code;
        private String msg;
        private Object data;

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setRespStatus(RespStatus respStatus) {
            this.code = respStatus.getCode();
            this.msg = respStatus.getMsg();
            return this;
        }

        public Builder setData(Object data) {
            this.data = data;
            return this;
        }

        public Resp build() {
            return new Resp(this);
        }
    }
}
