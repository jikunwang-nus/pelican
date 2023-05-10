package com.charsmart.data.account.domain;

import com.charsmart.data.account.repository.BdAccount;
import com.charsmart.data.account.repository.BdAccountService;
import com.charsmart.data.api.layer.AccountService;
import com.charsmart.data.common.Result;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 3:47 PM
 */
@DubboService
public class AccountServiceImpl implements AccountService {
    private final BdAccountService bdAccountService;

    public AccountServiceImpl(BdAccountService bdAccountService) {
        this.bdAccountService = bdAccountService;
    }

    @Override
    public Result reduce(BigDecimal reduce) {
        Optional<BdAccount> accountOptional = bdAccountService.query()
                .eq(BdAccount.ACCOUNT_NAME, "wonder").oneOpt();
        if (!accountOptional.isPresent()) return Result.fail("wonder 用户不存在");
        BdAccount account = accountOptional.get();
        if (account.getBalance().compareTo(reduce) < 0) return Result.fail("余额不足");
        BigDecimal balance = account.getBalance().subtract(reduce);
        account.setBalance(balance);
        bdAccountService.updateById(account);
        return Result.success();
    }

    @Override
    public Result increase(BigDecimal add) {
        return null;
    }
}
