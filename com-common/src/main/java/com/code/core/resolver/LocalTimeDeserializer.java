package com.code.core.resolver;


import com.code.common.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 用途描述
 *
 * @date 2021-06-11
 */
@Slf4j
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText().trim();
        if (Objects.equals(Constants.EMPTY_STR, source)) {
            return null;
        }
        return LocalTime.parse(source);
    }
}
