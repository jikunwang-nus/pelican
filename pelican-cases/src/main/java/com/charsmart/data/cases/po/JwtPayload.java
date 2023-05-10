package com.charsmart.data.cases.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午10:29
 */
@Getter
@Setter
@Accessors(chain = true)
public class JwtPayload {
    private String iss;
    private String exp;
    private String sub;
    private String aud;
    private String nbf;
    private String iat;
    private String jti;
    private String name;
}
