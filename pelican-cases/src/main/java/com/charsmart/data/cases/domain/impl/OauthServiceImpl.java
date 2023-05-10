package com.charsmart.data.cases.domain.impl;

import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.utils.JsonUtils;
import com.charsmart.data.cases.constant.JwtConstant;
import com.charsmart.data.cases.domain.OauthService;
import com.charsmart.data.cases.po.JwtHeader;
import com.charsmart.data.cases.po.JwtPayload;
import com.charsmart.data.cases.tool.HMACUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午7:42
 */
@Service
public class OauthServiceImpl implements OauthService {
    private final Map<String, String> userSecretMap = new HashMap<>();
    private final String SECRET_KEY = "WONDER";

    public OauthServiceImpl() {
        userSecretMap.put("admin", "123456");
    }

    @Override
    public boolean checkSecret(String userName, String password) {
        return userSecretMap.containsKey(userName) && password.equals(userSecretMap.get(userName));
    }

    @Override
    public Result getToken(String userName, String password) {
        if (checkSecret(userName, password)) {
            return Result.success(createJwt(userName));
        }
        return Result.fail("login failed");
    }

    @Override
    public Result verifyToken(String token) {
        String[] split = token.split("\\.");
        if (split.length != 3)
            return Result.fail("JWT格式错误");
        String header = split[0];
        String payload = split[1];
        String signatureInput = split[2];
        /*校验签名*/
        String signature;
        try {
            signature = HMACUtil.hmacWithJava("HmacSHA256", header + "." + payload, SECRET_KEY);
        } catch (Exception e) {
            throw new RuntimeException("获取签名异常");
        }
        if (signature.equals(signatureInput)) {
            return Result.success();
        }
        return Result.fail();
    }

    private String createJwt(String userName) {
        JwtHeader header = JwtConstant.jwtHeader;
        String headStr = JsonUtils.toJson(header);
        String headerBase64 = Base64Utils.encodeToUrlSafeString(headStr.getBytes());
        JwtPayload payload = new JwtPayload().setSub("oauth").setName(userName);
        String payloadBase64 = Base64Utils.encodeToUrlSafeString(JsonUtils.toJson(payload).getBytes());
        String signature;
        try {
            signature = HMACUtil.hmacWithJava("HmacSHA256", headerBase64 + "." + payloadBase64, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return headerBase64 + "." + payloadBase64 + "." + signature;
    }

}
