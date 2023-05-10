package com.charsmart.data.cases.domain.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.react.NameDTO;
import com.charsmart.data.cases.react.NameTreeNode;
import com.charsmart.data.cases.tool.SnowflakeIdGenerator;
import com.charsmart.data.cases.domain.NameDomainService;
import com.charsmart.data.cases.entity.Name;
import com.charsmart.data.cases.service.NameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/31 上午10:29
 */
@Service
public class NameDomainServiceImpl implements NameDomainService {
    private final NameService nameService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public NameDomainServiceImpl(NameService nameService, SnowflakeIdGenerator snowflakeIdGenerator) {
        this.nameService = nameService;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    @Override
    public Result save(NameDTO dto) {
        String parentName = dto.getParentName();
        if (parentName == null || "".equals(parentName)) {
            return Result.fail("parent name is null");
        }
        QueryChainWrapper<Name> eq = nameService.query().eq(Name.NAME, parentName);
        if (dto.getParentId() != null) {
            eq.eq(Name.ID, dto.getParentId());
        }
        Name one = eq.one();
        List<String> children = dto.getChildren();
        List<Name> names = new ArrayList<>();
        if (one == null) {
            one = new Name().setId(snowflakeIdGenerator.nextId())
                    .setName(parentName);
            names.add(one);
            int index = 1;
            for (String child : children) {
                Name name = new Name().setId(snowflakeIdGenerator.nextId())
                        .setName(child)
                        .setParent(parentName)
                        .setSequence(index)
                        .setParentId(one.getId());
                names.add(name);
                index++;
            }
        } else {
            List<Name> list = nameService.query().eq(Name.PARENT, parentName).list();
            Optional<Name> max = list.stream().filter(t -> t.getSequence() != null).max(Comparator.comparing(Name::getSequence));
            int index = max.map(value -> value.getSequence() + 1).orElse(1);
            Set<String> exist = list.stream().map(Name::getName).collect(Collectors.toSet());
            for (String child : children) {
                if (exist.add(child)) {
                    Name name = new Name().setId(snowflakeIdGenerator.nextId())
                            .setName(child)
                            .setParent(parentName)
                            .setParentId(one.getId())
                            .setSequence(index);
                    names.add(name);
                    index++;
                }

            }
        }
        nameService.saveBatch(names);

        return Result.success();
    }

    @Override
    public Result getTree(String rootName) {
        if (rootName == null) rootName = "大贵";
        List<Name> all = nameService.query().list();
        String finalRootName = rootName;
        Optional<Name> first = all.stream().filter(t -> t.getName().equals(finalRootName)).findFirst();
        if (!first.isPresent()) return Result.fail(rootName + "不存在");
        NameTreeNode root = new NameTreeNode();
        root.setName(rootName);
        root.setParentName(first.get().getParent());
        fillChildren(root, all);
        return Result.success(root);
    }

    private void fillChildren(NameTreeNode root, List<Name> all) {
        String name = root.getName();
        List<Name> children = all.stream().filter(t -> t.getParent() != null && t.getParent().equals(name))
                .sorted(Comparator.comparing(Name::getSequence))
                .collect(Collectors.toList());
        List<NameTreeNode> nodes = new ArrayList<>();
        for (Name child : children) {
            NameTreeNode nameTreeNode = new NameTreeNode().setName(child.getName()).setParentName(name);
            fillChildren(nameTreeNode, all);
            nodes.add(nameTreeNode);
        }
        root.setChildren(nodes);
    }
}
