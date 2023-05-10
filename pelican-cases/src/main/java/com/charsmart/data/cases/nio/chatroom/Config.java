package com.charsmart.data.cases.nio.chatroom;

import com.charsmart.data.cases.nio.chatroom.protocal.Serializer;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: Wonder
 * @Date: Created on 2022/3/3 下午6:06
 */
public abstract class Config {
    static Properties properties;

    static {
        try (InputStream in = Config.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Serializer.Algorithm getAlgorithm() {
        String value = properties.getProperty("serializer-algorithm");
        if (value != null) {
            return Serializer.Algorithm.valueOf(value);
        }
        return Serializer.Algorithm.Json;
    }
}
