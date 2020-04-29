package com.uetty.sample.springboot.util;


import com.uetty.sample.springboot.enums.CodeableEnum;

/**
 * @Author: Vince
 * @Date: 2019/6/4 19:18
 */
public class EnumUtil {

    public static <T extends Enum<T> & CodeableEnum> T valueOf(Byte code, Class<T> clz) {
        if (code == null) return null;
        return valueOf(code.intValue(), clz);
    }

    public static <T extends Enum<T> & CodeableEnum> T valueOf(Integer code, Class<T> clz) {
        if (code == null) return null;

        T[] values = clz.getEnumConstants();

        for (T item : values) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public static <T extends Enum<T>> T valueOf(String name, Class<T> clz) {
        if (name == null) return null;
        T[] values = clz.getEnumConstants();
        for (T item : values) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static <T extends Enum<T>> T valueByCodeOrOrdinal(Integer code, Class<T> clz) {
        if (code == null) return null;

        T[] values = clz.getEnumConstants();
        if (CodeableEnum.class.isAssignableFrom(clz)) {

            for (T item : values) {
                CodeableEnum codeableEnum = (CodeableEnum) item;
                if (codeableEnum.getCode() == code) {
                    return item;
                }
            }
            return null;
        } else {
            for (T item : values) {
                if (item.ordinal() == code) {
                    return item;
                }
            }
            return null;
        }
    }
}
