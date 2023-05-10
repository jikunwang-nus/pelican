package com.charsmart.data.bytecode.structure.attributes;

import lombok.Data;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/5 11:02 PM
 */
@Data
public class ExceptionInfo {
    private int startPc;
    private int endPc;
    private int handlerPc;
    private int catchType;

    public ExceptionInfo instance(DataInputStream is) throws IOException {
        startPc = is.readUnsignedShort();
        endPc = is.readUnsignedShort();
        handlerPc = is.readUnsignedShort();
        catchType = is.readUnsignedShort();
        return this;
    }
}
