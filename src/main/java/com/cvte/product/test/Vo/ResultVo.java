package com.cvte.product.test.Vo;

import lombok.Data;

/**
 * @author cvte
 * @date 2022/8/8 2:33 PM
 */
@Data
public class ResultVo {
    private int status;
    private String message;
    private Object data;

    public ResultVo(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResultVo ok() {
        return new ResultVo(0, "success");
    }

    public static ResultVo fail(int status, String message) {
        return new ResultVo(status, message);
    }
    public ResultVo put(Object data){
        this.setData(data);
        return this;
    }
}
