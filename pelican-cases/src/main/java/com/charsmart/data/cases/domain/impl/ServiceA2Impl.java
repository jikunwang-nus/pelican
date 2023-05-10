package com.charsmart.data.cases.domain.impl;

import com.charsmart.data.cases.domain.ServiceA;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/31 下午5:00
 */
@Service
@Order(1)
public class ServiceA2Impl implements ServiceA {
    @Override
    public void show() {
        System.out.println("this is service 2");
    }
}
