package com.uetty.sample.springboot.enums;

public enum  VerificationCodeType implements CodeableEnum {

    /**
     * 登录
     */
    LOGIN(1),
    ;

    int code;

    VerificationCodeType(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

}