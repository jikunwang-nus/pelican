package com.charsmart.data.distributed.log;

import com.charsmart.data.distributed.log.constants.MessageLevel;
import com.charsmart.data.distributed.log.constants.MessageType;

import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/14 10:36
 */
public class LocalJVMRecord {
    private String traceId;
    private long jvmSequenceId;
    private long currentRowId = 1;
    private List<LogRow> rows;

    public void acceptMethodRecord(String methodName, int lineNo, String message, boolean invoke) {
        LogRow row = new LogRow(currentRowId++, invoke ? MessageType.INVOKE : MessageType.INFO,
                MessageLevel.INFO,
                methodName,
                String.valueOf(lineNo),
                message);
        rows.add(row);
    }
}
