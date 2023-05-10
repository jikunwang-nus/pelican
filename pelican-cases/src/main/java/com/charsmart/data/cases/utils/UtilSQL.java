package com.charsmart.data.cases.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/1 7:36 PM
 */
public class UtilSQL {
    public static void main(String[] args) {
        SQL sql = new SQL();
        List<String> tableNames = new ArrayList<>();
        List<String> selectColumns = new ArrayList<>();
        List<String> joinConditions = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        sql.SELECT("id").FROM("md_item")
                .WHERE("id=1")
                .WHERE("code like '1'");
        String s = sql.toString();
        System.out.println(sql.getSelf().toString());

    }
}
