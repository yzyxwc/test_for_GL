package com.example.demo.mapper;

import com.example.demo.entity.DedicatedLine;
import com.example.demo.entity.Order;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.TripAll;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO form(orderdate,ordertrip,orderdedicatedline,orderpeoplecount,directcustomerprice,settlementprice,orgernizerreturnpoint,orgernizerid,singleprofit)" +
            "VALUES(#{date},#{ordertrip},#{orderdedicatedline},#{orderpeoplecount},#{directcustomerprice},#{settlementprice},#{orgernizerreturnpoint},#{orgernizerid} ,#{singleprofit})")
    /*service处理完成的数据 进行插入操作*/
    Integer insertOrderSingle(Date date, Integer ordertrip, Integer orderdedicatedline, Integer orderpeoplecount,
                              BigDecimal directcustomerprice,BigDecimal settlementprice,BigDecimal orgernizerreturnpoint,
                              Integer orgernizerid,BigDecimal singleprofit);
    @Insert("INSERT INTO form(orderdate,ordertrip,orderdedicatedline,orderpeoplecount,directcustomerprice,settlementprice,orgernizerreturnpoint,orgernizerid,singleprofit)" +
            "VALUES(#{orderdate},#{intordertrip},#{intorderdedicatedline},#{orderpeoplecount},#{directcustomerprice},#{settlementprice},#{orgernizerreturnpoint},#{intorgernizerid} ,#{singleprofit})")
    @Options(useGeneratedKeys=true, keyProperty="orderid", keyColumn="orderid")
    Integer insertOrderObject(Order order);
    @Select("SELECT * FROM form WHERE orderdelete = 0")
    @Results({
            @Result(property = "ordertrip", column = "ordertrip", javaType = TripAll.class,
                    one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "orderdedicatedline", column = "orderdedicatedline", javaType = DedicatedLine.class,
                    one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById")),
            @Result(property = "orgernizerid", column = "orgernizerid", javaType = Organizer.class,
                    one = @One(select = "com.example.demo.mapper.OrganizerMapper.selectOrganizerById")),
    })
    List<Order> getOrderList();


}
