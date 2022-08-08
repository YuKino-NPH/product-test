package com.cvte.product.test.Vo;

/**
 * @author cvte
 * @date 2022/8/8 2:33 PM
 */
public class ResultVo {
    private int code;
    private String message;
    private Object data;

    public ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultVo ok() {
        return new ResultVo(200, "success");
    }

    public static ResultVo fail(int code, String message) {
        return new ResultVo(code, message);
    }
    public ResultVo put(Object data){
        this.setData(data);
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
