package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 7:37 PM
 */
public interface Acceptable {
    ConstantPoolTopEntry accept(DataInputStream is) throws IOException;
}
