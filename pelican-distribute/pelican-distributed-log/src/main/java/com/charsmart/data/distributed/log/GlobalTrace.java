package com.charsmart.data.distributed.log;

import com.charsmart.data.distributed.log.context.GlobalTraceContextHolder;

import java.util.UUID;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/14 10:30
 */
public class GlobalTrace {
    private LocalJVMRecord localJVMRecord;
    private long traceId;

    public long getTraceId() {
        return this.traceId;
    }

    public void setLocalJVMRecord(LocalJVMRecord localJVMRecord) {
        this.localJVMRecord = localJVMRecord;
    }

    public LocalJVMRecord getLocalJVMRecord() {
        return this.localJVMRecord;
    }

    private void setTraceId(long traceId) {
        this.traceId = traceId;
    }

    GlobalTrace(long traceId) {
        this.traceId = traceId;
    }

}
