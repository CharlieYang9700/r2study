package com.code.core.resolver;

import com.code.common.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 用途描述
 *
 * @date 2021-06-11
 */
@Slf4j
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final String TIME_ZONE_TAG = "T";
    private static final String NOT_TIMESTAMP_TAG = "-";
    /**
     * 日期时间字节长度
     */
    private static final int DATE_TIME_BYTE_LENGTH = 14;
    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter YMDMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText().trim();
        if (Objects.equals(Constants.EMPTY_STR, source)) {
            return null;
        }
        if (source.contains(TIME_ZONE_TAG)) {
            return LocalDateTime.parse(source);
        }
        if (!source.contains(NOT_TIMESTAMP_TAG)) {
            long timestamp = Long.valueOf(source);
            return LocalDateTime.ofEpochSecond(timestamp / 1000, (int) (timestamp % 1000), ZoneOffset.ofHours(8));
        }
        if (source.length() > DATE_TIME_BYTE_LENGTH) {
            return LocalDateTime.parse(source, YMDMS);
        } else {
            return LocalDateTime.of(LocalDate.parse(source, YMD), LocalTime.MIN);
        }
    }
}
