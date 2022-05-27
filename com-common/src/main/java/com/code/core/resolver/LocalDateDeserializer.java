package com.code.core.resolver;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 用途描述
 *
 * @author king
 * @date 2021-06-11
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final String YMD = "yyyy-MM-dd";


    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText().trim();
        if ("".equals(source)) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(YMD));
    }
}
