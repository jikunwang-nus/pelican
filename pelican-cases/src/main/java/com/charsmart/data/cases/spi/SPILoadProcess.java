package com.charsmart.data.cases.spi;

import com.charsmart.data.spi.api.Print;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/13 6:59 PM
 */
public class SPILoadProcess {
    public static void main(String[] args) {
        ServiceLoader<Print> load = ServiceLoader.load(Print.class);
        Iterator<Print> iterator = load.iterator();
        while (iterator.hasNext()) {
            Print next = iterator.next();
            next.print("1");
        }
    }
}
