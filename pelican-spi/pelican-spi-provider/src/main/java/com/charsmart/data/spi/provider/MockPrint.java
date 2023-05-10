package com.charsmart.data.spi.provider;

import com.charsmart.data.spi.api.Print;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/13 5:15 PM
 */
public class MockPrint implements Print {
    @Override
    public void print(String msg) {
        System.out.println("mock print");
    }
}
