package com.charsmart.data.bytecode.structure.constantpool;

import lombok.Data;

import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 6:43 PM
 */
@Data
public class ConstantPool {
    private List<ConstantPoolTopEntry> entries;

    public String getUTF8Constant(int index) {
        ConstantPoolTopEntry entry = entries.get(index);
        if (entry instanceof ConstantUTF8) {
            return ((ConstantUTF8) entry).getVal();
        }
        throw new RuntimeException("not a utf8 constant");
    }
}
