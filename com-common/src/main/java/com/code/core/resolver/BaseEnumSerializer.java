package com.code.core.resolver;

import com.code.core.base.AbstractEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author ping
 */
public class BaseEnumSerializer extends JsonSerializer<AbstractEnum> {
    @Override
    public void serialize(AbstractEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeObject(value.getValue());
        }
    }
}