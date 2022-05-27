package com.code.mybatis.handler;


import com.code.biz.BizException;
import com.code.mybatis.MybatisPlusConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用途描述
 *
 *
 * @author ping
 * @date 2021-05-15
 */
@Slf4j
public class JsonObjectHandler<T> extends BaseTypeHandler<T> {

    private static ObjectMapper objectMapper = MybatisPlusConfig.getObjectMapper();

    private Class<T> type;

    public JsonObjectHandler(Class<T> type) {
        if (log.isTraceEnabled()) {
            log.trace("JsonTypeHandler(" + type + ")");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    private T parse(String json) throws BizException {
        try {
            if (json == null || json.length() == 0) {
                return null;
            }
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json反序列化出错", e);
            throw new BizException("json反序列化出错");
        }
    }

    private String toJsonString(Object obj) throws BizException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错", e);
            throw new BizException("json序列化出错");
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return parse(rs.getString(columnName));
        } catch (BizException e) {
            log.error("json序列化出错", e);
            throw new SQLException("json反序列化出错");
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return parse(rs.getString(columnIndex));
        } catch (BizException e) {
            log.error("json反序列化出错", e);
            throw new SQLException("json反序列化出错");
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return parse(cs.getString(columnIndex));
        } catch (BizException e) {
            log.error("json反序列化出错", e);
            throw new SQLException("json反序列化出错");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int columnIndex, T parameter, JdbcType jdbcType)
            throws SQLException {
        try {
            ps.setString(columnIndex, toJsonString(parameter));
        } catch (BizException e) {
            log.error("json序列化出错", e);
            throw new SQLException("json序列化出错");
        }
    }
}
