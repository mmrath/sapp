package com.mmrath.sapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static ObjectMapper jsonMapper = new ObjectMapper();

    private JsonUtils(){
    }
    public static String asJsonString(Object obj) {
        try {
            return jsonMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
    