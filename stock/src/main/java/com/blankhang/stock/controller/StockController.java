package com.blankhang.stock.controller;

import com.blankhang.stock.service.IStockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * StockController
 *
 * @author blank
 * @version 2023-06-10 18:29:24
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private IStockService stockService;


    @RequestMapping("/deduct")
    public void deduct(String commodityCode, Integer count){

        stockService.deduce(commodityCode,count);

    }

}
