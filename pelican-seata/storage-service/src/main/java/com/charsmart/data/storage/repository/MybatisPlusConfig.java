package com.charsmart.data.storage.repository;

import com.charsmart.data.storage.repository.mapper.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/13 3:23 PM
 */
@Configuration
@MapperScan(basePackageClasses = Mapper.class)
public class MybatisPlusConfig {
}
