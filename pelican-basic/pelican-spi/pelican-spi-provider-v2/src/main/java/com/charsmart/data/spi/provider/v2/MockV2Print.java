package com.charsmart.data.spi.provider.v2;

import com.charsmart.data.spi.api.Print;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/13 5:33 PM
 */
public class MockV2Print implements Print {
    @Override
    public void print(String msg) {
        System.out.println("mock v2 print!");
    }
}
