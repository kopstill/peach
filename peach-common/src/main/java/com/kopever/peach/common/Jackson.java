package com.kopever.peach.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.io.StringWriter;

public class Jackson {

    public static String toJson(Object object) {
        if (object != null) {
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = JacksonInstance.INSTANCE.getInstance();
            try {
                mapper.writeValue(writer, object);
                return writer.toString();
            } catch (IOException e) {
                throw new RuntimeException("Jackson write value exception");
            }
        }

        throw new NullPointerException("Target object is null");
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json != null) {
            ObjectMapper mapper = JacksonInstance.INSTANCE.getInstance();
            try {
                return mapper.readValue(json, clazz);
            } catch (IOException e) {
                throw new RuntimeException("Jackson read value exception", e);
            }
        }

        throw new NullPointerException("Target json is null");
    }

    public static <T> T convert(Object object, Class<T> clazz) {
        if (object != null) {
            ObjectMapper mapper = JacksonInstance.INSTANCE.getInstance();
            return mapper.convertValue(object, clazz);
        }

        throw new NullPointerException("Target json is null");
    }

}

enum JacksonInstance {

    INSTANCE;

    private ObjectMapper mapper;

    JacksonInstance() {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ObjectMapper getInstance() {
        return mapper;
    }

}
