package com.charsmart.data.cases.transaction;

import com.charsmart.data.cases.entity.SysUser;
import com.charsmart.data.cases.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/12 下午5:14
 */
@Service
public class TransactionB {
    private final SysUserService userService;

    public TransactionB(SysUserService userService) {
        this.userService = userService;
    }

    @Transactional(propagation = Propagation.NESTED)
    public void execute() {
        userService.save(new SysUser().setUserId("B").setUserName("transactionB")
                .setCreateDate(LocalDateTime.now()).setCreateBy("1"));
    }
}
