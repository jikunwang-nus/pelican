package com.charsmart.data.cases.utils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/8 下午4:00
 */
public class HexUtils {
    public static String hexToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void test() {

    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        long timestamp = now.plusHours(1).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(timestamp);
        System.out.println(now.minusHours(3).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
    }
}
