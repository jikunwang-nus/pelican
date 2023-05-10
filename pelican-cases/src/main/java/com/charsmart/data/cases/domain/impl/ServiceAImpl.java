package com.charsmart.data.cases.domain.impl;

import com.charsmart.data.cases.domain.ServiceA;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/31 下午4:47
 */
@Service
@Order(-1)
public class ServiceAImpl implements ServiceA {
    @Override
    public void show() {
        System.out.println("this is service 12");
    }


}

