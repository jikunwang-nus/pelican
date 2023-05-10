package com.charsmart.data.distributed.log;

import java.util.UUID;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/14 10:30
 */
public class GlobalTrace {
    private final ThreadLocal<LocalJVMRecord> tlJVMRecord = new ThreadLocal<>();
    private final ThreadLocal<String> tlTraceId = new ThreadLocal<>();

    public void trace(String message, boolean invoke) {
        String traceId = tlTraceId.get();
        if (traceId == null) {
            initGlobalTrace();
            traceId = tlTraceId.get();
        }
        /*获取当前的链路状态*/
        LocalJVMRecord jvmRecord = tlJVMRecord.get();
        if (jvmRecord == null) {
            initJvmRecord();
            jvmRecord = tlJVMRecord.get();
        }
        /*获取执行trace的方法*/
        StackTraceElement stackCallerMethod = getStackCallerMethod();
        jvmRecord.acceptMethodRecord(stackCallerMethod.getMethodName(),stackCallerMethod.getLineNumber(), message, invoke);
    }

    public void trace(String message) {
        trace(message, false);
    }

    private StackTraceElement getStackCallerMethod() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return elements[1];
    }

    private void initGlobalTrace() {
        String traceId = UUID.randomUUID().toString();
        tlTraceId.set(traceId);
    }

    private void initJvmRecord() {

    }
}
