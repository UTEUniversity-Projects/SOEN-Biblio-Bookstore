package com.biblio.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtil {

    private String value;
    private String json;

    public HttpUtil() {
    }

    public HttpUtil(String value) {
        this.value = value;
        this.json = value;
    }

    public <T> T toModel(Class<T> tClass) throws JsonProcessingException {
        return new ObjectMapper().readValue(value, tClass);
    }
    
    public <T> T toModelUnknown(Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON to model: " + e.getMessage(), e);
        }
    }

    public static HttpUtil of(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = reader.readLine()) != null) {
            sb.append(line);
        }

        System.out.println(sb.toString());

        return new HttpUtil(sb.toString());
    }
}
