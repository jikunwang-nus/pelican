package com.charsmart.data.cases.controller;

import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.domain.OauthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午7:40
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @PutMapping("/token")
    public Result token(String userName, String password) {
        return oauthService.getToken(userName, password);
    }

    @GetMapping("/verify-token")
    public Result verifyToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) return Result.fail();
        if (!authorization.startsWith("Bearer ")) return Result.fail();
        String token = authorization.substring(7);
        return oauthService.verifyToken(token);
    }
}
