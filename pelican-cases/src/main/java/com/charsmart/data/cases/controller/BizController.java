package com.charsmart.data.cases.controller;

import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.domain.DomainService;
import com.charsmart.data.cases.domain.ServiceA;
import com.charsmart.data.cases.domain.impl.DomainServiceImpl;
import com.charsmart.data.cases.transaction.TransactionA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/17 上午11:32
 */
@RestController
@RequestMapping("/biz")
public class BizController {
    private final DomainService service;
    private final TransactionA transactionA;
    @Autowired
    public List<ServiceA> serviceAs;

    public BizController(DomainServiceImpl service, TransactionA transactionA) {
        this.service = service;
        this.transactionA = transactionA;
    }

    @GetMapping("/user-info")
    public Result userInfo(String userName) {
        return service.userInfo(userName);
    }

    @PutMapping("/save")
    public Result saveUser(String userName, String phoneNum) {
        return service.saveUser(userName, phoneNum);
    }

    @PostConstruct
    public void init() {
        for (ServiceA serviceA : serviceAs) {
            serviceA.show();
        }
    }

    @GetMapping("/propagation")
    public String propagation() {
        transactionA.execute();
        return "ok";
    }

}
