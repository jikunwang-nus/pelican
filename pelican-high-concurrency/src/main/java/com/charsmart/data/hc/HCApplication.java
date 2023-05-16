package com.charsmart.data.hc;

import com.charsmart.data.api.layer.StorageService;
import com.charsmart.data.common.Result;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/23 15:07
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.data.hc")
@RestController
@RequestMapping("/hc")
@MapperScan(basePackages = "com.charsmart.data.hc.repository.mapper")
public class HCApplication {
    @Autowired
    private StorageService storageService;


    public static void main(String[] args) {
        SpringApplication.run(HCApplication.class, args);
    }

    @PutMapping("/storage-reduce")
    public Result storage(String id) {
        return storageService.reduce(id, 1);
    }
}
