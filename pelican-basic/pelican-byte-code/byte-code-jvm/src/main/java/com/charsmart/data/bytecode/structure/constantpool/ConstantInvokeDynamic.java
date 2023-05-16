package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 10:53 PM
 */
public class ConstantInvokeDynamic extends ConstantPoolTopEntry implements Acceptable {
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public ConstantInvokeDynamic(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        bootstrapMethodAttrIndex = is.readUnsignedShort();
        nameAndTypeIndex = is.readUnsignedShort();
        return this;
    }
}
