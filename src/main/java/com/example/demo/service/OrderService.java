package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Order;
import com.example.demo.entity.Result;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    /*查询所有*/
    public List<Order> getOderList(){
        return orderMapper.getOrderList();
    }
    /*插入单条*/
    public Result insertOrderSingle(String strDate, Integer ordertrip, Integer orderdedicatedline, String orderpeoplecountArr,
                                    String strdirectcustomerprice, String strsettlementprice, String strorgernizerreturnpoint,
                                    Integer orgernizerid){
            if(strDate == null || ordertrip == null || orderdedicatedline == null ||
                    strdirectcustomerprice == null || strsettlementprice == null ||
                    strorgernizerreturnpoint == null || orgernizerid == null){
                return Result.getResult(ExceptionEnum.DATA_ERR);
            }
            Date orderdate = null;
            List<Integer> list;
            BigDecimal directcustomerprice;
            BigDecimal settlementprice;
            BigDecimal orgernizerreturnpoint;
            BigDecimal singleprofit;
            BigDecimal zero = new BigDecimal("0");
            try{
                orderdate = StrUtil.stringToDate(strDate);
                list = JSON.parseArray(orderpeoplecountArr,Integer.class);
                directcustomerprice = StrUtil.stringToBigDecimal(strdirectcustomerprice);
                settlementprice = StrUtil.stringToBigDecimal(strsettlementprice);
                orgernizerreturnpoint = StrUtil.stringToBigDecimal(strorgernizerreturnpoint);
            }catch (Exception e){
                return Result.getResult(ExceptionEnum.FORMATTER_ERR_DATA);
            }
            if((directcustomerprice.subtract(settlementprice.add(orgernizerreturnpoint))).doubleValue()<0){
                return Result.getResult(ExceptionEnum.LOSS_ERR_DATA);
            }
            //计算出单人利润
            singleprofit = directcustomerprice.subtract(settlementprice.add(orgernizerreturnpoint));
            Order order= new Order(orderdate,ordertrip,orderdedicatedline,list.size(),directcustomerprice,settlementprice,orgernizerreturnpoint,orgernizerid,singleprofit);
            /*1.检查重复数据 2.生成该订单并返回id 3.插入customerorder*/
        Integer intOrderCount =  orderMapper.insertOrderObject(order);
        //Integer intOrderCount =  orderMapper.insertOrderSingle(date,ordertrip,orderdedicatedline,list.size(),directcustomerprice,settlementprice,orgernizerreturnpoint,orgernizerid,singleprofit);
        if(!intOrderCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        //打印的插入之后的id
        System.out.println(order.getOrderid());
        return Result.getResult(ExceptionEnum.OP_SUCCESS);

    }
    //获取id所对应的订单
    public Result getOrderById(Integer id) {
        if(id == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Order order = orderMapper.getOrderById(id);
        if(null == order){
            return Result.getResult(ExceptionEnum.NO_DATA);
        }
        return  Result.getResult(ExceptionEnum.OP_SUCCESS,order);
    }
//模糊搜索
    public Result getOrderVague(String strDate, String ordertrip, String orderdedicatedline, Integer orderpeoplecountint, String orgernizer) {
        Date date;
        String ordertripname = StrUtil.formateVager(ordertrip);
        String dedicatedlinename = StrUtil.formateVager(orderdedicatedline);
        String orgernizername = StrUtil.formateVager(orgernizer);
        try {
            date = StrUtil.stringToDate(strDate);
        }catch (Exception p){
            date = null;
        }
        List<Order> list = orderMapper.getOrderVague(date,ordertripname,dedicatedlinename,orderpeoplecountint,orgernizername);
        return Result.getResult(ExceptionEnum.OP_SUCCESS,list);
    }
}
