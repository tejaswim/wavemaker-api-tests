package com.wavemaker.api.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);

    public static String toJSON(Object object, boolean prettify) throws IOException {
        return prettify ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object) : toJSON(object);
    }

    public static String toJSON(Object object) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            LOGGER.info("Unable to convert " + object + "to JSON", e);
        }
        return jsonString;
    }

    public static List<String> toJSONReturnValue(String jsonResponse, String key) {
        List<String> values = new ArrayList<String>();
        try {
            if (!jsonResponse.startsWith("[")) {
                JSONObject jsonObject = null;

                jsonObject = new JSONObject(jsonResponse);
                values.add(jsonObject.get(key).toString());

            } else {
                JSONArray jsonObjects = new JSONArray(new JSONTokener(jsonResponse));
                for (int i = 0; i < jsonObjects.length(); i++) {
                    values.add(((JSONObject) jsonObjects.get(i)).get("key").toString());
                }
            }
        } catch (JSONException e) {
        }
        return values;
    }

    public static <T> T toObject(String jsonString, Class<T> t) throws IOException {
        return (T) objectMapper.readValue(jsonString, t);
    }

    public static <T> T toObject(InputStream jsonStream, Class<T> t) throws IOException {
        return (T) objectMapper.readValue(jsonStream, t);
    }

    public static <T> T toObject(File file, Class<T> targetClass) throws IOException {
        return (T) objectMapper.readValue(file, targetClass);
    }

    public static <T> T toObject(File file, JavaType javaType) throws IOException {
        return (T) objectMapper.readValue(file, javaType);
    }

    public static <T> T toObject(String jsonString, TypeReference valueTypeRef) throws IOException {
        return (T) objectMapper.readValue(jsonString, valueTypeRef);
    }
}