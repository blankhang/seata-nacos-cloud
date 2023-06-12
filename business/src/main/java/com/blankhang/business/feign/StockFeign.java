package com.blankhang.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * StockFeign
 *
 * @author blank
 * @version 2023-06-10 18:53:39
 */
@FeignClient(value = "stock")
public interface StockFeign {
    @RequestMapping("/stock/deduct")
    void deduct (@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);

}
