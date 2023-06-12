package com.blankhang.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * OrderFeign
 *
 * @author blank
 * @version 2023-06-10 18:50:39
 */
@FeignClient(value = "order")
public interface OrderFeign {
    @RequestMapping("/order/add")
    void add (@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count, @RequestParam("amount") BigDecimal amount);

}
