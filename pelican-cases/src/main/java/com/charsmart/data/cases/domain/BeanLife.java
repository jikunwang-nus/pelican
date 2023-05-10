package com.charsmart.data.cases.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/23 下午3:52
 */
@Service
public class BeanLife implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {
    @Autowired
    private DomainService domainService;


    @Override
    public void setBeanName(String name) {
        System.out.println("invoke the BeanNameAware接口的setBeanName方法");
        System.out.println(name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("invoke the BeanFactory接口的setBeanFactory方法");
        System.out.println(beanFactory);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("invoke the InitializingBean接口的afterPropertiesSet方法");
    }

    public void init() {
        System.out.println("invoke init 初始化方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("invoke DisposableBean接口的destroy方法");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("invoke pre destroy");
    }
}
