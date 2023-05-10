package com.charsmart.data.cases.domain.impl;

import com.charsmart.data.cases.config.RemoteConfig;
import com.charsmart.data.cases.common.Result;
import com.charsmart.data.cases.domain.BeanLife;
import com.charsmart.data.cases.domain.DomainService;
import com.charsmart.data.cases.entity.SysUser;
import com.charsmart.data.cases.mapper.SysUserMapper;
import com.charsmart.data.cases.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/17 上午11:31
 */
@Service
@Lazy
public class DomainServiceImpl implements DomainService {
    @Autowired
    private BeanLife beanLife;
    private SysUserService userService;
    private final RemoteConfig remoteConfig;

    private final SysUserMapper userMapper;

    public DomainServiceImpl(SysUserService userService, RemoteConfig remoteConfig, SysUserMapper userMapper) {
        this.userService = userService;
        this.remoteConfig = remoteConfig;
        this.userMapper = userMapper;
    }

    @Override
    public Result userInfo(String userName) {
//        Optional<SysUser> sysUser = userService.query().eq(SysUser.USER_NAME, userName).oneOpt();
//        if (sysUser.isPresent()) {
//            return Result.success(sysUser);
//        }
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveUser(String userName, String phoneNum) {
        SysUser sysUser = new SysUser().setUserName(userName).setTelephone(phoneNum).setUserId("1")
                .setCreateBy("1").setCreateDate(LocalDateTime.now());
        userService.save(sysUser);
        SysUser sysUser2 = new SysUser().setUserName(userName).setTelephone(phoneNum);
        userService.save(sysUser2);
        return Result.success();
    }
}
