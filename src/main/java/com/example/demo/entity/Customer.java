package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer customerid;
    private Integer customerdelete;
    private String  customername;
    private String  customeridcard;
}
