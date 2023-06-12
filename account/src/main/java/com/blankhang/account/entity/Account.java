package com.blankhang.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Account
 *
 * @author blank
 * @version 2023-06-10 19:31:02
 */
@Data
@TableName("t_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1470711462540728986L;


    @TableId(type = IdType.AUTO)
    public Integer id;

    public String userId;
    public BigDecimal amount;

}
