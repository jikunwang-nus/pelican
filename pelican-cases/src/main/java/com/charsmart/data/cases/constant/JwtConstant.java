package com.charsmart.data.cases.constant;

import com.charsmart.data.cases.po.JwtHeader;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午10:20
 */
public class JwtConstant {
    public static final JwtHeader jwtHeader = new JwtHeader().setAlg("HS256").setTyp("JWT");
}
