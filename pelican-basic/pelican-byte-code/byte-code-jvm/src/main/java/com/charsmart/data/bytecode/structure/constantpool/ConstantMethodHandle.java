package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 10:50 PM
 */
public class ConstantMethodHandle extends ConstantPoolTopEntry implements Acceptable{
    private int referenceKind;
    private int referenceIndex;
    public ConstantMethodHandle(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        referenceKind = is.readByte();
        referenceIndex = is.readUnsignedShort();
        return this;
    }
}
