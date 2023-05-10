package com.charsmart.data.cases.controller;

import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.domain.NameDomainService;
import com.charsmart.data.cases.react.NameDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/31 上午10:25
 */
@RestController
@RequestMapping("/name")
public class NameController {
    private final NameDomainService nameDomainService;

    public NameController(NameDomainService nameDomainService) {
        this.nameDomainService = nameDomainService;
    }

    @PutMapping("/save")
    public Result save(@RequestBody NameDTO dto) {
        return nameDomainService.save(dto);

    }

    @GetMapping("/tree")
    public Result tree(String rootName) {
        return nameDomainService.getTree(rootName);
    }
}
