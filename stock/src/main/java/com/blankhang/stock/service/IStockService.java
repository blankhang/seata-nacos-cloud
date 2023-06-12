package com.blankhang.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blankhang.stock.entity.Stock;

public interface IStockService extends IService<Stock> {

    /**
     * 减少库存
     * @param commodityCode 产品代码
     * @param count 减少数量
     */
    void deduce (String commodityCode, Integer count);
}
