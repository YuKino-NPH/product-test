package com.cvte.product.test.enums;

/**
 * @author cvte
 * @date 2022/8/9 9:24 AM
 */
public enum ProductProInfoVerificationErrorEnum {
    PRODUCT_ID_NULL_ERROR(4000003,"产品代码不能为空"),
    PAGE_DATA_ERROR(4000003,"分页参数错误"),
    ;

    private Integer code;
    private String msg;

    ProductProInfoVerificationErrorEnum(Integer code, String msg) {
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
        return "ProductProInfoVerificationErrorEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
