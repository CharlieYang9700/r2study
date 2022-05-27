package com.code.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * 用途描述
 *
 *
 * @date 2021-05-15
 */
@Slf4j
public class LocalDateHandler extends BaseTypeHandler<LocalDate> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LocalDate dateTime, JdbcType jdbcType)
            throws SQLException {
        if (Objects.nonNull(dateTime)) {
            preparedStatement.setTimestamp(i,
                    new Timestamp(dateTime.atTime(0, 0).toEpochSecond(ZoneOffset.of("+8")) * 1000));
        }
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnName);
        if (Objects.isNull(timestamp)) {
            return null;
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnIndex);
        if (Objects.isNull(timestamp)) {
            return null;
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }

    @Override
    public LocalDate getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        Timestamp timestamp = callableStatement.getTimestamp(columnIndex);
        if (Objects.isNull(timestamp)) {
            return null;
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }
}
