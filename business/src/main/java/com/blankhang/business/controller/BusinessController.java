package com.blankhang.business.controller;

import com.blankhang.business.feign.AccountFeign;
import com.blankhang.business.feign.OrderFeign;
import com.blankhang.business.feign.StockFeign;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 *
 * API 入口 Controller
 * BusinessController
 *
 * @author blank
 * @version 2023-06-10 18:29:24
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private OrderFeign orderFeign;
    @Resource
    private StockFeign stockFeign;
    @Resource
    private AccountFeign accountFeign;


    /**
     * 开启全局分布式事务 处理订单
     *
     * @param userId        用户ID
     * @param commodityCode 商品代码
     * @param count         购买数量
     * @param amount        消费金额
     *
     * @author blank
     * @date 2023-06-10 下午 8:14
     */
    @GlobalTransactional
    @RequestMapping("/toOrder")
    public void toOrder (String userId, String commodityCode, Integer count, BigDecimal amount) {
        accountFeign.reduce(userId, amount);
        stockFeign.deduct(commodityCode, count);
        orderFeign.add(userId, commodityCode, count, amount);
    }

}
