package com.charsmart.data.hc.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/23 16:59
 */
@Mapper
public interface SQLMapper {

    @Update("update bd_storage set INVENTORY = INVENTORY- #{number} where ITEM_ID = #{id}")
    int reduce(@Param("id") String id, @Param("number") Integer number);
}
