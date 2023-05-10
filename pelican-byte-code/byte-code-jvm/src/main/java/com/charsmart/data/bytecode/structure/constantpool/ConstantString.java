package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 10:41 PM
 */
public class ConstantString extends ConstantPoolTopEntry implements Acceptable{
    private int stringIndex;
    public ConstantString(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        stringIndex = is.readUnsignedShort();
        return this;
    }
}
