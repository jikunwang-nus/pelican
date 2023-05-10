package com.charsmart.data.cases.react;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/31 上午10:27
 */
@Data
@Accessors(chain = true)
public class NameDTO {
    private String parentId;
    private String parentName;
    private List<String> children;
}
