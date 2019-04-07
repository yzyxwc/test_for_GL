package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一个订单对应的客户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    private Integer customerorderid;
    private Integer customerorderdelete;
    private Customer  customerid;
    private String  orderid;
}
