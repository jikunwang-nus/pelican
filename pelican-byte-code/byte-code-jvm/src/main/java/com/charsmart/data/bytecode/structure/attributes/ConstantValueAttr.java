package com.charsmart.data.bytecode.structure.attributes;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/5 9:49 PM
 */
public class ConstantValueAttr extends AttributeInfo {
    private int constantValueIndex;

    @Override
    AttributeInfo accept(DataInputStream is) throws IOException {
        AttributeInfo accept = super.accept(is);
        constantValueIndex = is.readUnsignedShort();
        return accept;
    }
}
