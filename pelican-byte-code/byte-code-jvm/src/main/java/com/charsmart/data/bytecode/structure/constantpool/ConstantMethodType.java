package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 10:51 PM
 */
public class ConstantMethodType extends ConstantPoolTopEntry implements Acceptable{
    private int descriptorIndex;
    public ConstantMethodType(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        descriptorIndex = is.readUnsignedShort();
        return this;
    }
}
