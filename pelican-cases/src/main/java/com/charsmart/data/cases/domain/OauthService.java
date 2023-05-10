package com.charsmart.data.cases.domain;

import com.charsmart.data.cases.common.Result;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午7:41
 */
public interface OauthService {
    boolean checkSecret(String userName, String password);

    Result getToken(String userName, String password);

    Result verifyToken(String token);
}
