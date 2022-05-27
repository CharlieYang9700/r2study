package com.code.mybatis.handler;

import com.code.core.base.AbstractEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author ping
 */
public class AutoEnumTransformHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private BaseTypeHandler typeHandler = null;

    public AutoEnumTransformHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        if (AbstractEnum.class.isAssignableFrom(type)) {
            /**
             * 如果实现了自定义接口则使用自定义的转换器
             */
            typeHandler = new IntegerEnumTypeHandler(type);
        } else {
            /**
             * 默认转换器 也可换成 EnumOrdinalTypeHandler
             */
            typeHandler = new EnumTypeHandler<>(type);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int index, E parameter, JdbcType jdbcType)
            throws SQLException {
        typeHandler.setNonNullParameter(ps, index, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (E) typeHandler.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (E) typeHandler.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (E) typeHandler.getNullableResult(cs, columnIndex);
    }
}