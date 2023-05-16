package com.charsmart.data.api.layer;

import com.charsmart.data.common.Result;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 3:48 PM
 */
public interface StorageService {
    Result reduce(String itemCode, Integer count);

    Result increase(String itemCode, Integer count);
}
