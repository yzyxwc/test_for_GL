package com.example.demo.entity;

import lombok.Data;

/**
 * @author wc
 * 返回的当月利润
 */
@Data
public class ThisMonthProfit {
    //当前月
    private String month;
    //订单量
    private Integer size;
    //闲散支出
    private Double cost;
    //毛利
    private Double profit;
    //支出项
    private Integer costSize;
    //净利润
    private Double monthProfit;

    public ThisMonthProfit(String month, Integer size, Double cost,Integer costSize, Double profit, Double monthProfit) {
        this.month = month;
        this.size = size;
        this.cost = cost;
        this.profit = profit;
        this.costSize = costSize;
        this.monthProfit = monthProfit;
    }
}
