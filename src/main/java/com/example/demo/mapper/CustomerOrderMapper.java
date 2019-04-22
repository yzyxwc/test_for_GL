package com.example.demo.mapper;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CustomerOrderMapper {
    /**
     * 单条插入
     * @param customerOrder
     * @return
     */
    @Insert("INSERT INTO customerorder (customerid,orderid)VALUES(#{customerid},#{orderid})")
    Integer insertCustomerOrderSingle(CustomerOrder customerOrder);
    @Update("UPDATE customerorder SET customerorderdelete = 1 WHERE orderid = #{id}")
    Integer deleteCustomerOrderByOderId(Integer id);
    @Update("UPDATE customerorder SET customerorderdelete = 1 WHERE customerorderdelete = 0 AND orderid = #{orderId} AND customerid = #{customerId}")
    Integer deleteCustomerOrderByOderIdAndCustomerId(Integer orderId,Integer customerId);
    @Select("select * from customer where customerid in(SELECT customerid from customerorder where  customerorderdelete = 0 AND orderid =#{id})")
    List<Customer> getCustemerByOderId(Integer id);
    @Select("Select * from  customerorder WHERE orderid = #{orderId} AND customerid = #{customerId} AND customerorderdelete = 0")
    CustomerOrder selectCustomerOrderByOrderIdAndCustomerId(Integer orderId, Integer customerId);
    @Update("UPDATE form set orderpeoplecount = (SELECT COUNT(*) as count  from  customerorder a where a.customerorderdelete = 0 and a.orderid = #{orderId} and a.orderId = orderId) where orderdelete = 0 and orderid = #{orderId}")
    Integer updateOrderByCountPeople(Integer orderId);
}
