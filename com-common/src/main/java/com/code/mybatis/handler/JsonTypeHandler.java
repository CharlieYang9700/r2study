package com.code.mybatis.handler;


import com.code.biz.BizException;
import com.code.core.base.AbstractEnum;
import com.code.mybatis.JsonMap;
import com.code.mybatis.MybatisPlusConfig;
import com.code.utils.CommonUtil;
import com.code.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 用途描述
 *
 * @author 86150
 * @date 2021-05-15
 */
@Slf4j
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private static final ObjectMapper objectMapper = MybatisPlusConfig.getObjectMapper();

    private T parse(String json, String key) throws BizException {
        try {
            if (json == null || json.length() == 0) {
                return null;
            }
            key = key.toLowerCase();
            if (MybatisPlusConfig.JSON_TYPE_MAP.get(key) != null) {
                return objectMapper.readValue(json, MybatisPlusConfig.JSON_TYPE_MAP.get(key));
            } else {
                throw new IllegalStateException("没有找到" + key + "对应的handler");
            }
        } catch (IOException e) {
            if ("MapType".equals(MybatisPlusConfig.JSON_TYPE_MAP.get(key).getClass().getSimpleName())) {
                JavaType keyType = MybatisPlusConfig.JSON_TYPE_MAP.get(key).getKeyType();
                JavaType valueType = MybatisPlusConfig.JSON_TYPE_MAP.get(key).getContentType();
                Map<String, Object> map = JsonUtil.parse(json);
                if (CommonUtil.isNotEmpty(map)) {
                    JsonMap<AbstractEnum, Object> jsonMap = new JsonMap<>(map.size());
                    AbstractEnum abstractEnum;
                    for (String keyValue : map.keySet()) {
                        abstractEnum = (AbstractEnum) AbstractEnum.findByName(keyValue, (Class<? extends Enum>) keyType.getRawClass());
                        jsonMap.put(abstractEnum, JsonUtil.parse(JsonUtil.toJsonString(map.get(keyValue)), valueType));
                    }
                    return (T) jsonMap;
                } else {
                    return (T) new JsonMap<>(1);
                }
            } else {
                log.error("json反序列化出错", e);
                throw new BizException("json反序列化出错");
            }
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
            String tbName = rs.getMetaData().getTableName(rs.findColumn(columnName));
            return parse(rs.getString(columnName), String.format("%s.%s", tbName, columnName));
        } catch (BizException e) {
            log.error("json序列化出错", e);
            throw new SQLException("json序列化出错");
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String columnName = rs.getMetaData().getColumnName(columnIndex);
            String tbName = rs.getMetaData().getTableName(columnIndex);
            return parse(rs.getString(columnIndex), String.format("%s.%s", tbName, columnName));
        } catch (BizException e) {
            log.error("json反序列化出错", e);
            throw new SQLException("json反序列化出错");
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String tbName = cs.getMetaData().getTableName(columnIndex);
            String columnName = cs.getMetaData().getColumnName(columnIndex);
            return parse(cs.getString(columnIndex), String.format("%s.%s", tbName, columnName));
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
            log.error("json反序列化出错", e);
            throw new SQLException("json反序列化出错");
        }
    }
}
