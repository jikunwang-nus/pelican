package com.charsmart.data.bytecode.structure.attributes;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/5 9:50 PM
 */
public class CodeAttr extends AttributeInfo {
    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private byte[] code;
    private String codeVal;
    private int exceptionTableLength;
    private ExceptionInfo[] exceptionTable;
    private AttributeInfo[] attributes;

    @Override
    AttributeInfo accept(DataInputStream is) throws IOException {
        super.accept(is);
        maxStack = is.readUnsignedShort();
        maxLocals = is.readUnsignedShort();
        codeLength = is.readInt();
        code = new byte[codeLength];
        is.read(code);
        codeVal = new String(code, StandardCharsets.UTF_8);
        exceptionTableLength = is.readUnsignedShort();
        exceptionTable = new ExceptionInfo[exceptionTableLength];
        int count = exceptionTableLength;
        while (count > 0) {
            exceptionTable[exceptionTable.length - count] = new ExceptionInfo().instance(is);
            count--;
        }
        attributes = AttributeInfo.createAttrArray(getCp(), is);
        return this;
    }
}
