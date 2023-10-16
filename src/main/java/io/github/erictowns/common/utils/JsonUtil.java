package io.github.erictowns.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.erictowns.common.exception.JsonSerializationException;

import java.util.List;
import java.util.Map;

/**
 * desc: Json Util
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 17:19
 */
public final class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 反序列化：JSON字段中有Java对象中没有的字段时不报错
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化：序列化BigDecimal时不使用科学计数法输出
        OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        // 序列化: 序列化时忽略null值
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private JsonUtil() {
    }

    public static <T> T toObject(String string, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(string, cls);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException(e);
        }
    }

    public static <T> T toObject(String str, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(str, valueTypeRef);
        } catch (JsonProcessingException | RuntimeException e) {
            throw new JsonSerializationException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Map toMap(String str) {
        try {
            return OBJECT_MAPPER.readValue(str, Map.class);
        } catch (JsonProcessingException | RuntimeException e) {
            throw new JsonSerializationException(e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException | RuntimeException e) {
            throw new JsonSerializationException(e);
        }
    }

    public static <T> List<T> toObjectList(String string, TypeReference<List<T>> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(string, typeReference);
        } catch (JsonProcessingException | RuntimeException e) {
            throw new JsonSerializationException(e);
        }
    }

    public static JsonNode toTree(String string) {
        try {
            return OBJECT_MAPPER.readTree(string);
        } catch (JsonProcessingException | RuntimeException e) {
            throw new JsonSerializationException(e);
        }
    }

}
