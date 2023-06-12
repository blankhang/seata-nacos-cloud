package com.blankhang.stock.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blankhang.stock.entity.Stock;
import com.blankhang.stock.mapper.StockMapper;
import com.blankhang.stock.service.IStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * StockServiceImpl
 *
 * @author blank
 * @version 2023-06-10 20:05:08
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

    @Resource
    private StockMapper stockMapper;

    /**
     * 减少库存
     *
     * @param commodityCode 产品代码
     * @param count         减少数量
     */
    @Override
    public void deduce (String commodityCode, Integer count) {

        Stock stock = stockMapper.selectOne(Wrappers.lambdaQuery(Stock.class)
                .eq(Stock::getCommodityCode, commodityCode)
        );

        if (stock != null && stock.getCount() >= count) {

            stock.setCount(count - stock.getCount());
            stockMapper.updateById(stock);
        } else {
            throw new RuntimeException("Not enough stock");
        }

    }
}
