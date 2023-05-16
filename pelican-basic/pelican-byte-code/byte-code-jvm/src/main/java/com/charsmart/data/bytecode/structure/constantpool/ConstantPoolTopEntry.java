package com.charsmart.data.bytecode.structure.constantpool;

import lombok.Data;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 7:25 PM
 */
@Data
public class ConstantPoolTopEntry {
    private int tag;

    public ConstantPoolTopEntry(int tag) {
        this.tag = tag;
    }
}
