package com.uetty.sample.springboot.enums;

/**
 * 用户状态（预留）
 */
public enum UserStatusEnum implements CodeableEnum {

    /** 预留默认值 */ NORMAL(1),
    ;

    int code;

    UserStatusEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    
}
