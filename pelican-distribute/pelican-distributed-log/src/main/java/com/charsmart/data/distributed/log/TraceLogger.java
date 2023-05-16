package com.charsmart.data.distributed.log;

import com.charsmart.data.distributed.log.context.GlobalTraceContextHolder;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/10 17:37
 */
public class TraceLogger {
    public static void trace(String message, boolean invoke) {
        GlobalTrace gt = GlobalTraceContextHolder.get();
        if (gt == null) {
            gt = initGlobalTrace();
        }
        /*获取当前的链路状态*/
        LocalJVMRecord jvmRecord = gt.getLocalJVMRecord();
        if (jvmRecord == null) {
            jvmRecord = initJvmRecord();
        }
        /*获取执行trace的方法*/
        StackTraceElement stackCallerMethod = getStackCallerMethod();
        String methodName = stackCallerMethod.getClassName() + "#" + stackCallerMethod.getMethodName();
        jvmRecord.acceptMethodRecord(methodName, stackCallerMethod.getLineNumber(), message, invoke);
    }

    public static void trace(String message) {
        trace(message, false);
    }

    public static void call(String message) {
        trace(message, true);
    }

    private static StackTraceElement getStackCallerMethod() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            String className = elements[i].getClassName();
            if (!className.equals("com.charsmart.data.distributed.log.TraceLogger")) {
                return elements[i];
            }
        }
        throw new RuntimeException("no caller method found !");
    }

    private static GlobalTrace initGlobalTrace() {
        long traceId = 1;
        GlobalTrace instance = new GlobalTrace(traceId);
        GlobalTraceContextHolder.set(instance);
        return instance;
    }

    private static LocalJVMRecord initJvmRecord() {
        GlobalTrace trace = GlobalTraceContextHolder.get();
        LocalJVMRecord jvmRecord = new LocalJVMRecord();
        trace.setLocalJVMRecord(jvmRecord);
        return jvmRecord;
    }
}
