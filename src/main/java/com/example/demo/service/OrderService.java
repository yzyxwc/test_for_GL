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
}
