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

    private static final String I18N_MESSAGE_KEY = "reponse.message.code_";
    /**
     * 默认中国区域的消息返回值
     */
    @SuppressWarnings("unused")
    public String getDefaultMessage() {
        return getDefaultMessage(Locale.CHINA);
    }

    /**
     * 国际化消息返回值
     */
    public String getDefaultMessage(Locale locale) {
        if (ApplicationVariable.APPLICATION_CONTEXT == null) {
            return null;
        }
        try {
            if (messageSource == null) {
                messageSource = ApplicationVariable.APPLICATION_CONTEXT.getBean(MessageSource.class);
            }
            if (locale == null) {
                locale = Locale.CHINA;
            }
            return messageSource.getMessage(I18N_MESSAGE_KEY + code, null, locale);
        } catch (Exception ignore) {}
        return null;
    }
}
