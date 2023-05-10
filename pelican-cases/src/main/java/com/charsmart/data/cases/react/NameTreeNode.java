package com.charsmart.data.cases.react;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/31 上午11:51
 */
@Data
@Accessors(chain = true)
public class NameTreeNode {
    private String name;
    private String parentName;
    private List<NameTreeNode> children;
}
