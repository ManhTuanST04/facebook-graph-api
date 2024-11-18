package com.mb.social.network.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Phương thức để chuyển đổi đối tượng Java sang chuỗi JSON
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    // Phương thức để chuyển đổi chuỗi JSON thành đối tượng Java
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    // Phương thức để chuyển đổi chuỗi JSON thành đối tượng Java với kiểu List
    public static <T> T fromJsonToList(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(java.util.List.class, clazz));
    }
}
