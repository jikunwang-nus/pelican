package com.charsmart.data.distributed.log.context;


import com.charsmart.data.distributed.log.GlobalTrace;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/10 17:11
 */
public final class GlobalTraceContextHolder {
    private static final ThreadLocal<GlobalTrace> tlGT = new ThreadLocal<>();

    GlobalTraceContextHolder() {
    }

    public static void set(GlobalTrace gt) {
        tlGT.set(gt);
    }

    public static GlobalTrace get() {
        return tlGT.get();
    }

    public static void clear() {
        tlGT.remove();
    }
}
