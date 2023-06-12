package com.blankhang.account.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blankhang.account.entity.Account;
import com.blankhang.account.mapper.AccountMapper;
import com.blankhang.account.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * AccountServiceImpl
 *
 * @author blank
 * @version 2023-06-10 19:35:08
 */
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 减少余额
     *
     * @param userId 用户ID
     * @param amount 要减少的金额
     */
    @Override
    public void reduce (String userId, BigDecimal amount) {

        Account account = accountMapper.selectOne(Wrappers.lambdaQuery(Account.class).eq(Account::getUserId, userId));

        if (account == null) {
            throw new RuntimeException("Account not correct");
        }

        if (account.getAmount() == null || account.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Not enough balance");
        }

        BigDecimal subtract = account.getAmount().subtract(amount);
        account.setAmount(subtract);

        accountMapper.updateById(account);
    }
}
