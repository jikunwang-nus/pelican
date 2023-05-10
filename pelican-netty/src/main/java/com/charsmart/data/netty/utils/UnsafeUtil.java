package com.charsmart.data.netty.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/14 19:06
 */
public class UnsafeUtil {
    static sun.misc.Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static final Unsafe unsafe = getUnsafe();
    public static Unsafe unsafe() {
        return unsafe;
    }
}
