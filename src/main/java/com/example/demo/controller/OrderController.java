package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.Result;
import com.example.demo.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/getOrderList")
    @ApiOperation(value = "获取所有订单",notes = "获取所有订单")
    public  List<Order> getOrderList(){
        return orderService.getOderList();
    }
    @PostMapping("/insertOrderSingle")
    @ApiOperation(value = "插入单条订单",notes = "插入单条订单")
    public Result insertOrderSingle(String strDate, Integer ordertrip, Integer orderdedicatedline, String orderpeoplecountArr,
                                            String strdirectcustomerprice, String strsettlementprice, String strorgernizerreturnpoint,
                                            Integer orgernizerid){
        return orderService.insertOrderSingle(strDate,ordertrip,orderdedicatedline,orderpeoplecountArr,
                strdirectcustomerprice,strsettlementprice,strorgernizerreturnpoint,orgernizerid);
    }
    @GetMapping("/getOrderById/{id}")
    @ApiOperation(value = "获取id所对应的订单",notes = "获取id所对应的订单")
    public  Result getOrderById(@PathVariable("id") Integer id){
        return orderService.getOrderById(id);
    }
    @GetMapping("/getOrderVague")
    @ApiOperation(value = "获取模糊订单",notes = "获取模糊的订单（创建时间，行程名，专线名，人数，组织者）")
    public  Result getOrderVague(String strDate, String ordertrip, String orderdedicatedline, Integer orderpeoplecountint, String orgernizer){
        return orderService.getOrderVague(strDate,ordertrip,orderdedicatedline,orderpeoplecountint,orgernizer);
    }
}
