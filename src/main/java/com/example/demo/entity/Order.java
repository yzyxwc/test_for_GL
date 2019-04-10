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
    private TripAll ordertrip;
    private Integer intordertrip;
    private DedicatedLine orderdedicatedline;
    private Integer intorderdedicatedline;
    private Integer orderpeoplecount;
    private BigDecimal directcustomerprice;
    private BigDecimal settlementprice;
    private BigDecimal orgernizerreturnpoint;
    private Organizer orgernizerid;
    private Integer intorgernizerid;
    private BigDecimal singleprofit;

    public Order(Date orderdate, Integer intordertrip, Integer intorderdedicatedline,
                 Integer orderpeoplecount, BigDecimal directcustomerprice, BigDecimal settlementprice, BigDecimal orgernizerreturnpoint,
                 Integer intorgernizerid, BigDecimal singleprofit) {
        this.orderdate = orderdate;
        this.intordertrip = intordertrip;
        this.intorderdedicatedline = intorderdedicatedline;
        this.orderpeoplecount = orderpeoplecount;
        this.directcustomerprice = directcustomerprice;
        this.settlementprice = settlementprice;
        this.orgernizerreturnpoint = orgernizerreturnpoint;
        this.intorgernizerid = intorgernizerid;
        this.singleprofit = singleprofit;
    }
    public Order(Integer orderid,Integer orderdelete,Date orderdate, Integer intordertrip, Integer intorderdedicatedline,
                 Integer orderpeoplecount, BigDecimal directcustomerprice, BigDecimal settlementprice, BigDecimal orgernizerreturnpoint,
                 Integer intorgernizerid, BigDecimal singleprofit) {
        this.orderid = orderid;
        this.orderdelete=orderdelete;
        this.orderdate = orderdate;
        this.intordertrip = intordertrip;
        this.intorderdedicatedline = intorderdedicatedline;
        this.orderpeoplecount = orderpeoplecount;
        this.directcustomerprice = directcustomerprice;
        this.settlementprice = settlementprice;
        this.orgernizerreturnpoint = orgernizerreturnpoint;
        this.intorgernizerid = intorgernizerid;
        this.singleprofit = singleprofit;
    }
}
