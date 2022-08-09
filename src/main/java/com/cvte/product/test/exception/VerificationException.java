package com.cvte.product.test.exception;

/**
 * @author 聂裴涵
 * @date 2022/8/5 3:27 PM
 */
public class VerificationException extends RuntimeException{
    private Integer code;
    private Object msg;

    public VerificationException(Integer code, Object msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "VerificationException{" +
                "code=" + code +
                ", msg=" + msg +
                '}';
    }
}
