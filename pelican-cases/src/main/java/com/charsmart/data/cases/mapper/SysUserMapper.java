package com.charsmart.data.cases.mapper;

import com.charsmart.data.cases.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wonder@charsmart.com
 * @since 2021-12-17
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> queryUser(String name);

    List<SysUser> queryUser();
}
