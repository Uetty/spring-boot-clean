package com.uetty.sample.springboot.enums;

import com.uetty.sample.springboot.util.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis处理枚举类
 */
public class CodeableEnumTypeHandler<T extends Enum<T>> extends BaseTypeHandler<T> {

    private final Class<T> type;

    public CodeableEnumTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement pstmt, int i, T t, JdbcType jdbcType) throws SQLException {
        if (isCodeable()) {
            CodeableEnum codeableEnum = (CodeableEnum) t;
            pstmt.setInt(i, codeableEnum.getCode());
        } else {
            pstmt.setInt(i, t.ordinal());
        }
    }

    private boolean isCodeable() {
        return CodeableEnum.class.isAssignableFrom(type);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (code == 0 && rs.wasNull()) {
            return null;
        }
        return EnumUtil.valueByCodeOrOrdinal(code, type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int i) throws SQLException {
        int code = rs.getInt(i);
        if (code == 0 && rs.wasNull()) {
            return null;
        }
        return EnumUtil.valueByCodeOrOrdinal(code, type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int i) throws SQLException {
        int code = cs.getInt(i);
        if (code == 0 && cs.wasNull()) {
            return null;
        }
        return EnumUtil.valueByCodeOrOrdinal(code, type);
    }
}
