package com.charsmart.data.cases.domain;

import com.charsmart.data.cases.common.Result;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/17 上午11:30
 */
public interface DomainService {
    Result userInfo(String userName);

    Result saveUser(String userName, String phoneNum);

    static void show() {
        System.out.println("111");
    }

    default void print() {
        System.out.println("111");
    }
}
