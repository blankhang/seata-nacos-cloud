package com.blankhang.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blankhang.account.entity.Account;

import java.math.BigDecimal;

public interface IAccountService extends IService<Account> {

    /**
     * 减少余额
     * @param userId 用户ID
     * @param amount 要减少的金额
     */
    void reduce (String userId, BigDecimal amount);
}
