package com.charsmart.data.common.utils;

import com.google.gson.Gson;

/**
 * @Author: Wonder
 * @Date: Created on 2022/3/4 上午10:38
 */
public class JsonUtils {
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
