package com.charsmart.data.spi.provider.v3;

import com.charsmart.data.spi.api.Print;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/13 7:07 PM
 */
public class MockV3Print implements Print {
    @Override
    public void print(String msg) {
        System.out.println("mock v3 print");
    }
}
