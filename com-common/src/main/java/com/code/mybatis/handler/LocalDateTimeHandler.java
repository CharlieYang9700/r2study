package com.code.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

/**
 * 用途描述
 *
 *
 * @author 86150
 * @date 2021-05-15
 */
@Slf4j
public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LocalDateTime dateTime,
            JdbcType jdbcType) throws SQLException {
        if (Objects.nonNull(dateTime)) {
            preparedStatement.setTimestamp(i, new Timestamp(dateTime.toEpochSecond(ZoneOffset.of("+8")) * 1000));
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        Date date = resultSet.getTimestamp(columnName);
        if (Objects.isNull(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        Date date = resultSet.getTimestamp(columnIndex);
        if (Objects.isNull(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        Date date = callableStatement.getTimestamp(columnIndex);
        if (Objects.isNull(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
