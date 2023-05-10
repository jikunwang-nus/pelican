package com.charsmart.data.cases.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/16 11:43 AM
 */
public class DateUtil {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "hello";
                }
        );
        String s = stringCompletableFuture.get();
        System.out.println("wait");
    }
}
