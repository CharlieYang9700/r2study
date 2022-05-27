package com.code.core.resolver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wangping
 * @date 2021年08月10日 17:11
 **/
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private static final String YMDMS = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (dateTime == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeObject(DateTimeFormatter.ofPattern(YMDMS).format(dateTime));
        }
    }
}
