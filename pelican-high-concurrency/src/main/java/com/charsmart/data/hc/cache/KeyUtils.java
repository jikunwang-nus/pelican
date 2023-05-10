package com.charsmart.data.hc.cache;

import java.util.UUID;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/23 21:03
 */
public class KeyUtils {
    public static String cacheKey(String id) {
        return "redis_storage_" + id;
    }

    public static String LockKey(String id) {
        return "lock_storage_" + id;
    }

    public static String uid() {
        return UUID.randomUUID().toString();
    }
}
