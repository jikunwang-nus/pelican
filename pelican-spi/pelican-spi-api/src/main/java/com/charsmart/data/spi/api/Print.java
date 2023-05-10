package com.charsmart.data.spi.api;

import org.apache.dubbo.common.extension.SPI;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/13 4:59 PM
 */
@SPI
public interface Print {
    void print(String msg);
}
