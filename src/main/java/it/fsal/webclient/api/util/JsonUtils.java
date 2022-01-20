package it.fsal.webclient.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class JsonUtils {

    private static final Map<PropertyNamingStrategy,ObjectMapper> SERIALIZERS = new HashMap<>();

    private static final ObjectMapper DEFAULT_SERIALIZER = new ObjectMapper();

    private JsonUtils() {
        //utility class
    }

    public static String toJsonString(Object toJson) {
        return toJsonString(toJson, DEFAULT_SERIALIZER);
    }

    public static String toJsonString(Object toJson, PropertyNamingStrategy namingStrategy) {
        return toJsonString(toJson, getSerializer(namingStrategy));
    }

    private static String toJsonString(Object toJson, ObjectMapper serializer) {
        try {
            return serializer.writeValueAsString(toJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static Map fromJson(String json) {
        return fromJson(json, Map.class);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return DEFAULT_SERIALIZER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Long jsonPropertyToLong(Map map, Object key) {
        Objects.requireNonNull(map, "Map cannot be null");

        Long res = null;

        Object property = map.get(key);
        if (property != null) {

            if (property instanceof Number) {
                res = ((Number) property).longValue();
            }
            
            else {
                throw new IllegalArgumentException("Object is not a number");
            }
        }

        return res;
    }

    public static <T> T convertValue(Map<String, String> params, Class<T> clazz) {
        return DEFAULT_SERIALIZER.convertValue(params,clazz);
    }

    private static ObjectMapper getSerializer(PropertyNamingStrategy strategy) {
        SERIALIZERS.computeIfAbsent(strategy, s -> new ObjectMapper().setPropertyNamingStrategy(s));
        return SERIALIZERS.get(strategy);
    }
}
