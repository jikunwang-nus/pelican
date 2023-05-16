package com.charsmart.data.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/20 6:44 PM
 */
@RestController
@RequestMapping("/config")
public class NacosValueController {
    @NacosValue(value = "${cacheable:false}", autoRefreshed = true)
    private boolean cacheable = false;

    @GetMapping("/cacheable")
    public boolean isCacheable() {
        return cacheable;
    }

    @NacosValue(value = "${env:default}", autoRefreshed = true)
    private String env = "default";
    @GetMapping("/env")
    public String curEnv() {
        return env;
    }
}
