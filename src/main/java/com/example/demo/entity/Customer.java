package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 客户
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer customerid;
    private Integer customerdelete;
    private Date customerdate;
    private String  customername;
    private String  customeridcard;
    private String  customerdescripe;
}
