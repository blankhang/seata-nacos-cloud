package com.blankhang.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Stock
 *
 * @author blank
 * @version 2023-06-10 20:02:02
 */
@Data
@TableName("t_stock")
public class Stock implements Serializable {


    private static final long serialVersionUID = -2282740584643267757L;


    @TableId(type = IdType.AUTO)
    public Integer id;

    public String name;
    public String commodityCode;
    public Integer count;

}
