package com.blankhang.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * AccountFeign
 *
 * @author blank
 * @version 2023-06-10 18:50:39
 */
@FeignClient(value = "account")
public interface AccountFeign {
    @RequestMapping("/account/reduce")
    void reduce (@RequestParam("userId") String userId, @RequestParam("amount") BigDecimal amount);

}
