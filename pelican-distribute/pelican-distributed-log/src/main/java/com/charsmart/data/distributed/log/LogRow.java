package com.charsmart.data.distributed.log;

import com.charsmart.data.distributed.log.constants.MessageLevel;
import com.charsmart.data.distributed.log.constants.MessageType;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/14 09:53
 */
public class LogRow {
    private long rowId;
    private String type;
    private String level;
    private String methodName;
    private String lineNo;
    private String content;

    public LogRow(long rowId,String type, String level, String methodName, String lineNo, String content) {
        this.rowId = rowId;
        this.type = type;
        this.level = level;
        this.methodName = methodName;
        this.lineNo = lineNo;
        this.content = content;
    }

    public LogRow(String content) {
        this.type = MessageType.INFO;
        this.level = MessageLevel.INFO;
        this.content = content;
    }
}
