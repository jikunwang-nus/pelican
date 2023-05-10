package com.charsmart.data.cases.domain;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/6 下午3:22
 */
public abstract class DomainAdapter {
    private String name;

    public abstract String getName();

    ;

    public String getInfo() {
        return "default info";
    }

    DomainAdapter() {
        name = "default name";
    }
}
