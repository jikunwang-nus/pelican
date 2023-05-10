package com.charsmart.data.cases.config;

import com.charsmart.data.cases.mapper.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * MybatisPlusConfig
 * </p>
 *
 * @author lanmi.xin@charsmart.com
 * @since 2020/3/12
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackageClasses = Mapper.class)
public class MybatisPlusConfig {

}
