package com.charsmart.data.cases.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午10:23
 */
@Getter
@Setter
@Accessors(chain = true)
public class JwtHeader {
    private String alg;
    private String typ;
}
