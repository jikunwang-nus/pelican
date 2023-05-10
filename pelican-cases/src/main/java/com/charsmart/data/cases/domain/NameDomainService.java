package com.charsmart.data.cases.domain;

import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.react.NameDTO;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/31 上午10:29
 */
public interface NameDomainService {
    Result save(NameDTO dto);

    Result getTree(String rootName);
}
