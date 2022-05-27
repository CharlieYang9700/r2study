package com.code.utils;


import com.code.core.resolver.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 用途描述
 *
 * @author king
 * @date 2021-05-30
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Map<String, JavaType> javaListTypeMap = new HashMap<>(32);
    private static JavaType MAP_JAVA_TYPE;
    private static JavaType LIST_MAP_TYPE;

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        simpleModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.registerModule(simpleModule);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, Boolean.TRUE);
        MAP_JAVA_TYPE = MAPPER.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Object.class);
        LIST_MAP_TYPE = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, MAP_JAVA_TYPE);
    }

    public static JavaType getJavaListType() {
        return LIST_MAP_TYPE;
    }

    public static JavaType getJavaMapType() {
        return MAP_JAVA_TYPE;
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    public static byte[] toBytes(Object ob) {
        if (ob == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsBytes(ob);
        } catch (JsonProcessingException e) {
            log.error("对象转为json失败", e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String toJsonString(Object ob) {
        if (ob == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            log.error("对象转为json失败", e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T parse(String json, Class<T> type) {
        if (CommonUtil.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T parse(String json, JavaType javaType) {
        if (CommonUtil.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Map<String, Object> parse(String json) {
        return parse(json, MAP_JAVA_TYPE);
    }

    public static <E> List<E> parseAsList(String json, Class<E> type) {
        if (CommonUtil.isBlank(json)) {
            return null;
        }
        if (javaListTypeMap.get(type.getName()) == null) {
            javaListTypeMap.put(type.getName(), MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type));
        }
        try {
            return MAPPER.readValue(json, javaListTypeMap.get(type.getName()));
        } catch (IOException e) {
            log.error(String.format("JSON字符串转换为对象失败:JSON_STR:%s",json), e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<Map<String, Object>> parseAsList(String json) {
        if (CommonUtil.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, LIST_MAP_TYPE);
        } catch (IOException e) {
            log.error("JSON字符串转换为对象数组失败", e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
