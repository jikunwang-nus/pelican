package com.charsmart.data.cases.domain;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/6 下午3:25
 */
public class Customer {
    private String name;

    interface ShowInfo {
        void show();

        void log();
    }

    interface PrintInfo {
        void print();
    }

    PrintInfo printInfo = () -> System.out.println("打印对象");
}
