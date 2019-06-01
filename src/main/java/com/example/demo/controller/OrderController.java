package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.Result;
import com.example.demo.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/getOrderList")
    @ApiOperation(value = "获取所有订单",notes = "获取所有订单")
    public  Result getOrderList(Integer size,Integer page){
        return orderService.getOderList(size,page-1);
    }
    @PostMapping("/insertOrderSingle")
    @ApiOperation(value = "插入单条订单",notes = "插入单条订单")
    public Result insertOrderSingle(String strDate, Integer ordertrip, Integer orderdedicatedline,
                                            String strdirectcustomerprice, String strsettlementprice, String strorgernizerreturnpoint,
                                            Integer orgernizerid){
        return orderService.insertOrderSingle(strDate,ordertrip,orderdedicatedline,
                strdirectcustomerprice,strsettlementprice,strorgernizerreturnpoint,orgernizerid);
    }
    @GetMapping("/getOrderById/{id}")
    @ApiOperation(value = "获取id所对应的订单",notes = "获取id所对应的订单")
    public  Result getOrderById(@PathVariable("id") Integer id){
        return orderService.getOrderById(id);
    }
    @PostMapping("/getOrderVague")
    @ApiOperation(value = "获取模糊订单",notes = "获取模糊的订单（创建时间，行程名，专线名，人数，组织者）")
    public  Result getOrderVague(String strDate, String ordertrip, String orderdedicatedline, Integer orderpeoplecountint,
                                 String orgernizer,Integer size,Integer page){
        return orderService.getOrderVague(strDate,ordertrip,orderdedicatedline,orderpeoplecountint,orgernizer,page-1,size);
    }
    @DeleteMapping("/deleteOrderById/{id}")
    @ApiOperation(value = "订单按照id进行删除",notes = "订单按照id进行删除")
    public Result deleteOrderById(@PathVariable("id") Integer id){
        return orderService.deleteOrderById(id);
    }
    @PostMapping("/updateOrderSingle")
    @ApiOperation(value = "修改单条订单",notes = "修改单条订单")
    public Result updateOrderSingle(Integer orderid,Integer orderdelete,String orderdate, Integer tripallid, Integer dedicatedlineid,
                                    String directcustomerprice, String settlementprice, String orgernizerreturnpoint,
                                    Integer organizerid){
        return orderService.updateOrderSingle(orderid,orderdelete,orderdate,tripallid,dedicatedlineid,directcustomerprice,settlementprice,orgernizerreturnpoint,organizerid);

    }
}
