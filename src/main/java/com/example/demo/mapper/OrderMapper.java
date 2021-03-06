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
            "VALUES(#{orderdate},#{intordertrip},#{intorderdedicatedline},0,#{directcustomerprice},#{settlementprice},#{orgernizerreturnpoint},#{intorgernizerid} ,#{singleprofit})")
    @Options(useGeneratedKeys=true, keyProperty="orderid", keyColumn="orderid")
    Integer insertOrderObject(Order order);
    @Select("SELECT * FROM form WHERE orderdelete = 0 order by orderid desc limit #{start},#{size}")
    @Results({
            @Result(property = "ordertrip", column = "ordertrip", javaType = TripAll.class,
                    one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "orderdedicatedline", column = "orderdedicatedline", javaType = DedicatedLine.class,
                    one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById")),
            @Result(property = "orgernizerid", column = "orgernizerid", javaType = Organizer.class,
                    one = @One(select = "com.example.demo.mapper.OrganizerMapper.selectOrganizerById")),
    })
    List<Order> getOrderList(Integer start,Integer size);
    @Select("SELECT * FROM form WHERE orderdelete = 0 AND orderid = #{id}")
    @Results({
            @Result(property = "ordertrip", column = "ordertrip", javaType = TripAll.class,
                    one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "orderdedicatedline", column = "orderdedicatedline", javaType = DedicatedLine.class,
                    one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById")),
            @Result(property = "orgernizerid", column = "orgernizerid", javaType = Organizer.class,
                    one = @One(select = "com.example.demo.mapper.OrganizerMapper.selectOrganizerById")),
            @Result(property = "orderid", column = "orderid"),
            @Result(property = "customerList", column = "orderid", javaType = List.class,
                    one = @One(select = "com.example.demo.mapper.CustomerOrderMapper.getCustemerByOderId")),
    })

    Order getOrderById(Integer id);

    @Select({"<script>",
                    "SELECT * FROM form WHERE orderdelete = 0 ",
                    "<when test='date!=\"%%\"'>",
                        "AND orderdate = #{date}",
                    "</when><when test='orderpeoplecountint!=null'>",
                        "AND orderpeoplecount = #{orderpeoplecountint}",
                    "</when><when test='ordertripname!=\"%%\"'>",
                        "AND ordertrip IN (SELECT tripallid FROM tripall WHERE tripalldelete = 0 AND tripallname LIKE #{ordertripname})",
                    "</when><when test='dedicatedlinename!=\"%%\"'>",
                        "AND orderdedicatedline IN (SELECT dedicatedlineid FROM " +
                                "dedicatedline WHERE dedicatedlinedelete = 0 AND " +
                                "dedicatedlinename LIKE #{dedicatedlinename})",
                    "</when><when test='orgernizername!=\"%%\"'>",
                        "AND orgernizerid IN ( SELECT organizerid FROM organizer WHERE " +
                                "organizerdelete = 0 AND organizername LIKE " +
                                "#{orgernizername})",
                    "</when>",
            "order by orderid desc",
            "</script>"})
    @Results({
            @Result(property = "ordertrip", column = "ordertrip", javaType = TripAll.class,
                    one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "orderdedicatedline", column = "orderdedicatedline", javaType = DedicatedLine.class,
                    one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById")),
            @Result(property = "orgernizerid", column = "orgernizerid", javaType = Organizer.class,
                    one = @One(select = "com.example.demo.mapper.OrganizerMapper.selectOrganizerById")),
    })
    List<Order> getOrderVague(@Param("date") String date, @Param("ordertripname") String ordertripname,
                              @Param("dedicatedlinename") String dedicatedlinename,
                              @Param("orderpeoplecountint")Integer orderpeoplecountint,
                              @Param("orgernizername") String orgernizername);


    @Select({"<script>",
            "SELECT * FROM form WHERE orderdelete = 0 ",
            "<when test='date!=\"%%\"'>",
            "AND orderdate = #{date}",
            "</when><when test='orderpeoplecountint!=null'>",
            "AND orderpeoplecount = #{orderpeoplecountint}",
            "</when><when test='ordertripname!=\"%%\"'>",
            "AND ordertrip IN (SELECT tripallid FROM tripall WHERE tripalldelete = 0 AND tripallname LIKE #{ordertripname})",
            "</when><when test='dedicatedlinename!=\"%%\"'>",
            "AND orderdedicatedline IN (SELECT dedicatedlineid FROM " +
                    "dedicatedline WHERE dedicatedlinedelete = 0 AND " +
                    "dedicatedlinename LIKE #{dedicatedlinename})",
            "</when><when test='orgernizername!=\"%%\"'>",
            "AND orgernizerid IN ( SELECT organizerid FROM organizer WHERE " +
                    "organizerdelete = 0 AND organizername LIKE " +
                    "#{orgernizername})",
            "</when>",
            "order by orderid desc limit #{start},#{size}",
            "</script>"})
    @Results({
            @Result(property = "ordertrip", column = "ordertrip", javaType = TripAll.class,
                    one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "orderdedicatedline", column = "orderdedicatedline", javaType = DedicatedLine.class,
                    one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById")),
            @Result(property = "orgernizerid", column = "orgernizerid", javaType = Organizer.class,
                    one = @One(select = "com.example.demo.mapper.OrganizerMapper.selectOrganizerById")),
    })
    List<Order> getOrderVagueList(@Param("date") String date, @Param("ordertripname") String ordertripname,
                              @Param("dedicatedlinename") String dedicatedlinename,
                              @Param("orderpeoplecountint")Integer orderpeoplecountint,
                              @Param("orgernizername") String orgernizername,
                              @Param("size") Integer size,
                              @Param("start") Integer start);


    @Update("UPDATE form SET orderdelete = 1 WHERE orderid = #{id}")
    Integer deleteOrderById(Integer id);
    @Update("UPDATE form SET orderdelete = #{orderdelete},orderdate = #{orderdate} ," +
            "ordertrip =#{intordertrip} ,orderdedicatedline = #{intorderdedicatedline}," +
            " directcustomerprice = #{directcustomerprice} ," +
            "settlementprice = #{settlementprice} , orgernizerreturnpoint = #{orgernizerreturnpoint} ," +
            "orgernizerid =#{intorgernizerid} ,singleprofit =#{singleprofit} " +
            "WHERE orderid = #{orderid}")
    Integer updateOrderSingle(Order order);
    @Select("SELECT * from form where orderdate BETWEEN #{start} and #{end}")
    List<Order> getOrderByMonth(@Param("start") String start , @Param("end") String end);

    @Select("SELECT * FROM form WHERE orderdelete = 0 order by orderid desc")
    @Results({
            @Result(property = "ordertrip", column = "ordertrip", javaType = TripAll.class,
                    one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "orderdedicatedline", column = "orderdedicatedline", javaType = DedicatedLine.class,
                    one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById")),
            @Result(property = "orgernizerid", column = "orgernizerid", javaType = Organizer.class,
                    one = @One(select = "com.example.demo.mapper.OrganizerMapper.selectOrganizerById")),
    })
    List<Order> findAll();
}
