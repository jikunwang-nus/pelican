package com.charsmart.data.cases.controller;

import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.domain.DomainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/1 下午2:50
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private final DomainService domainService;


    public TestController(DomainService domainService) {
        this.domainService = domainService;
    }

    @GetMapping("/user")
    public Result userInfo(String userName) {
        return domainService.userInfo(userName);
    }
}
