package com.charsmart.data.nacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/17 4:37 PM
 */
@RestController
@RequestMapping("/naming")
public class NamingController {
    @NacosInjected
    private NamingService namingService;

    @GetMapping("/list")
    public List<Instance> list(String serviceName) {
        try {
            return namingService.getAllInstances(serviceName);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/registry")
    public String registry(String serviceName, String ip, int port) {
        try {
            namingService.registerInstance(serviceName, ip, port);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }
}
