package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.service.CustomerOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerOrderController {
    @Autowired
    CustomerOrderService customerOrderService;
    @PostMapping("/insertCustomerOrderSingle")
    @ApiOperation(value = "插入单条CustomerOrder",notes = "插入单条CustomerOrder")
    public Result insertCustomerOrderSingle(Integer orderId,Integer customerId){
        return customerOrderService.insertCustomerOrderSingle(orderId,customerId);
    }
    @PostMapping("/deleteCustomerOrderSingle")
    @ApiOperation(value = "删除单条CustomerOrder",notes = "删除单条CustomerOrder")
    public Result deleteCustomerOrderSingle(Integer orderId,Integer customerId){
        return customerOrderService.deleteCustomerOrderSingle(orderId,customerId);
    }
}
