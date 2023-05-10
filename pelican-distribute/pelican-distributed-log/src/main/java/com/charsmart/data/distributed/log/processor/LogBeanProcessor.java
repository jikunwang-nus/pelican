package com.charsmart.data.distributed.log.processor;

import com.charsmart.data.distributed.log.annotations.ChainRecord;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/13 18:06
 */
public class LogBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        ChainRecord annotation = bean.getClass().getAnnotation(ChainRecord.class);
        return bean;
    }
}
