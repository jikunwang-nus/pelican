package com.charsmart.data.netty.utils;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/13 15:59
 */
public class StackUtils {
    public static String print() {
        StringBuilder builder = new StringBuilder();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            builder.append(element.getClassName())
                    .append(element.getMethodName())
                    .append(element.getLineNumber())
                    .append("\n");
        }
        return builder.toString();
    }
}
