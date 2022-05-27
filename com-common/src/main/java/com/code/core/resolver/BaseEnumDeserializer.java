package com.code.core.resolver;


import com.code.core.base.AbstractEnum;
import com.code.utils.CommonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

/**
 * json转换时解析枚举
 *
 * @date 2021-05-14
 */
public class BaseEnumDeserializer extends JsonDeserializer<AbstractEnum> implements ContextualDeserializer {

    private static final char ZERO = '0';
    private static final char NINE = '9';
    private Class clazz;

    Class getClazz() {
        return clazz;
    }

    void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public AbstractEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING || curr == JsonToken.FIELD_NAME) {
            if (CommonUtil.isBlank(jp.getText())) {
                return null;
            }
            // 为了防止枚举值但是使用了字符串来传递
            if (jp.getText().charAt(0) >= ZERO && jp.getText().charAt(0) <= NINE) {
                return (AbstractEnum) AbstractEnum.findByValue(Integer.parseInt(jp.getText().trim()), this.clazz);
            } else {
                return (AbstractEnum) AbstractEnum.findByName(jp.getText().trim().toUpperCase(), this.clazz);
            }
        }
        if (curr == JsonToken.VALUE_NUMBER_INT) {
            return (AbstractEnum) AbstractEnum.findByValue(jp.getIntValue(), this.clazz);
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        Class rawCls = ctxt.getContextualType().getRawClass();
        JsonFormat annotation = (JsonFormat) rawCls.getAnnotation(JsonFormat.class);
        if (annotation != null && annotation.shape() == JsonFormat.Shape.OBJECT) {
            throw new IllegalStateException("无法解析JsonFormat.Shape.OBJECT修饰的枚举对象");
        }
        BaseEnumDeserializer deserializer = new BaseEnumDeserializer();
        deserializer.setClazz(rawCls);
        return deserializer;
    }
}

