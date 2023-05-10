package com.charsmart.data.spi.dubbo.provider;

import com.charsmart.data.spi.api.Print;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/16 11:11 AM
 */
public class DubboMockPrint implements Print {
    @Override
    public void print(String msg) {
        System.out.println("this is dubbo print");
    }
}
