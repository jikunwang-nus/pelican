package com.charsmart.data.cases.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/29 上午10:46
 */
@Configuration
@ConfigurationProperties(prefix = "remote")
@Getter
@Setter
public class RemoteConfig {
//    @NestedConfigurationProperty
    private RemoteServiceConfig service;
}
