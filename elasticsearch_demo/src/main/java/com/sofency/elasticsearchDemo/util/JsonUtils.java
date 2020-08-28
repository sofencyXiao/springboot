package com.sofency.elasticsearchDemo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaohufeng
 * @date:
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * mk json obj to str
     * @param obj obj
     * @param <T> obj class type
     * @return string
     */
    public static <T> String toString(T obj) {
        try {
            if (Objects.isNull(obj)) {
                return "";
            }
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将inputString转为json对象
     * parse a json string to obj
     * @param input input str
     * @param clazz obj class
     */
    public static <T> T parseObject(String input, Class<T> clazz) {
        try {

            if (StringUtils.isNullOrEmpty(input)) {
                return null;
            }
            return mapper.readValue(input, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将inputString转为List
     * parse a json string to array
     * @param input input str
     * @param clazz obj class
     */
    public static <T> List<T> parseArray(String input, Class<T> clazz) {
        try {
            if (StringUtils.isNullOrEmpty(input)) {
                return null;
            }
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            return mapper.readValue(input, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将inputStr转为JsonNode
     * parse input string to JsonNode
     * @see JsonNode
     * @param input input string
     */
    public static JsonNode parseObject(String input) {
        try {
            if (StringUtils.isNullOrEmpty(input)) {
                throw new NullPointerException();
            }
            return mapper.readTree(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parse input to a complete object
     *
     */
    public static <T> T parseObject(String input, TypeReference<T> type) throws IOException {
        return mapper.readValue(input, type);
    }

}
