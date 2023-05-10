package com.charsmart.data.cases.transaction;

import com.charsmart.data.cases.entity.SysUser;
import com.charsmart.data.cases.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/12 下午5:13
 */
@Service
public class TransactionA {
    private final SysUserService userService;
    private final TransactionB transactionB;

    public TransactionA(SysUserService userService, TransactionB transactionB) {
        this.userService = userService;
        this.transactionB = transactionB;
    }

    @Transactional
    public void execute() {
        userService.save(new SysUser().setUserId("A").setUserName("transactionA")
                .setCreateDate(LocalDateTime.now()).setCreateBy("1"));
        try {
            transactionB.execute();
        } catch (Exception ex) {
            System.out.println("内层事务异常，外层依然要提交");
            ex.printStackTrace();
        }
    }
}
