package com.uetty.sample.springboot.enums;

/**
 * 用户在系统中的角色（非业务角色）
 */
public enum UserSysRoleEnum implements CodeableEnum {
    /** 系统角色-管理员 */ ADMIN(1),
    /** 系统角色-用户（默认值） */ USER(2),
    ;

    int code;

    UserSysRoleEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
