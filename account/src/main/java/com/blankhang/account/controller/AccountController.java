package com.blankhang.account.controller;

import com.blankhang.account.service.IAccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * AccountController
 *
 * @author blank
 * @version 2023-06-10 19:29:24
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private IAccountService accountService;

    @RequestMapping("/reduce")
    public void reduce (@RequestParam("userId") String userId, @RequestParam("amount") BigDecimal amount) {


        accountService.reduce(userId, amount);

    }

}
