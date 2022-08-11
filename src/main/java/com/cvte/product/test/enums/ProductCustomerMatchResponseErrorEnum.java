package com.cvte.product.test.enums;

import lombok.Data;

/**
 * @author cvte
 * @date 2022/8/9 3:38 PM
 */
public enum ProductCustomerMatchResponseErrorEnum {
    SELECT_ERROR(4000003,"模块不存在"),
    INSERT_EXIST_ERROR(4000003,"该产品信息已存在"),
    INSERT_ERROR(5000001,"系统错误"),
    DELETE_ERROR(4000003,"模块ID错误"),UPDATE_ERROR(4000003,"模块更新失败"),
    SELECT_FUZZY_MATCH_ERROR(4000003,"没有产品信息"),
    SELECT_FUZZY_NOT_PARAMETER(4000003,"不能无参数传入"),
    INSERT_NOT_PARAMETER(4000003,"参数不完整")
    ;
    private Integer code;
    private String msg;

    ProductCustomerMatchResponseErrorEnum(int code, String msg) {
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
        return "UserResponseEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
