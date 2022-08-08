package com.cvte.product.test.exception;

/**
 * @author 聂裴涵
 */
public class CustomGlobalException extends RuntimeException{
    private Integer code;
    private String msg;

    public CustomGlobalException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    public String toString() {
        return "CustomGlobalException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
