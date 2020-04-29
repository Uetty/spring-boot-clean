package com.uetty.sample.springboot.constant;

import com.uetty.sample.springboot.enums.CodeableEnum;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * api接口统一响应状态码
 * <p>响应消息内容需要在i18n目录下的国际化文件中写</p>
 */
public enum ResponseCode implements CodeableEnum {

    /** 成功 */ SUCCESS(1),

    /* 100_001 - 100_999 系统级错误 */
    /** 失败 */ FAILED(100_000),
    /** 内部错误 */ INTERNAL_ERROR(100_001),
    /* 登录失败 */ LOGIN_FAILED(100_002),
    /** 无权限 */ USER_HAS_NO_ACCESS(100_003),
    /** 404 */ STATUS_404(100_404),

    ;

    int code; // 返回码

    ResponseCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "name=" + name() +
                ",code=" + code +
                '}';
    }

    private static MessageSource messageSource;

    @Override
    public int getCode() {
        return this.code;
    }

}
