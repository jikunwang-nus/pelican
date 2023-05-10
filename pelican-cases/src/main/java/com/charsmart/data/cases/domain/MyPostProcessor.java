package com.charsmart.data.cases.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/23 下午3:59
 */
@Component
public class MyPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanLife) {
            System.out.println("invoke BeanPostProcessor实现类的postProcessBeforeInitialization方法");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanLife) {
            System.out.println("invoke BeanPostProcessor实现类的postProcessAfterInitialization方法");
        }
        return bean;
    }
}
