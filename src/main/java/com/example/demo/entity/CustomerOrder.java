package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 一个订单对应的客户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    private Integer customerorderid;
    private Integer customerorderdelete;
    private List<Customer> customer;
    private Customer singleCustemer;
    private Order orderObject;
    private Integer customerid;
    private Integer  orderid;

    public CustomerOrder(Integer customerid, Integer orderid) {
        this.customerid = customerid;
        this.orderid = orderid;
    }
}
