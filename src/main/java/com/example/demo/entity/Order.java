package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * 注释详见数据库
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer orderid;
    private Integer orderdelete;
    private Date orderdate;
    private Integer ordertrip;
    private Integer orderdedicatedline;
    private Integer orderpeoplecount;
    private BigDecimal directcustomerprice;
    private BigDecimal settlementprice;
    private BigDecimal orgernizerreturnpoint;
    private Integer orgernizerid;
    private BigDecimal singleprofit;
}
