package com.charsmart.data.api.layer;

import com.charsmart.data.common.Result;

import java.math.BigDecimal;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 3:36 PM
 */
public interface AccountService {
    Result reduce(BigDecimal reduce);

    Result increase(BigDecimal add);
}
