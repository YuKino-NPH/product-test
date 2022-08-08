package com.cvte.product.test.enums;
/**
 * @description: 产品信息服务执行错误时的返回值和状态吗枚举类
 * @author: 聂裴涵
 * @date: 2022/8/8 5:32 PM
 * @param:
 * @return:
 **/
public enum ProductProInfoResponseErrorEnum {
    SELECT_ERROR(4000003,"用户不存在"),INSERT_EXIST_ERROR(4000003,"该产品信息已存在"),
    DELETE_ERROR(4000003,"用户名错误"),UPDATE_ERROR(4000003,"用户ID错误"),
    INSERT_ERROR(5000001,"系统错误"),
    VERIFICATION_ERROR(4000002,"数据校验失败");
    private Integer code;
    private String msg;

    ProductProInfoResponseErrorEnum(int code, String msg) {
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
