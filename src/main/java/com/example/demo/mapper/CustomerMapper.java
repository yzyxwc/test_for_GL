package com.example.demo.mapper;

import com.example.demo.entity.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface CustomerMapper {
    /**
     * 插入单条用户
     * @param date
     * @param name
     * @param card
     * @param description
     * @return
     */
    @Insert("INSERT INTO " +
            "customer(customerdate,customername,customeridcard,customerdescripe) " +
            "VALUES (#{date},#{name},#{card},#{description})")
    Integer insertCustomerSingle(Date date,String name,String card,String description);

    /**
     * 查询客户
     * @param id
     * @return
     */
    @Select("SELECT * FROM customer WHERE customerid = #{id} AND customerdelete = 0")
    Customer getCustomerById(Integer id);

    /**
     * 查重
     * @param idCard
     * @return
     */
    @Select("SELECT * FROM customer " +
            "WHERE customeridcard = #{idCard} AND customerdelete = 0")
    Customer getCustomerByIdCard(String idCard);

    /**
     * 模糊查询（姓名，身份证，描述）
     * @param name
     * @param idCard
     * @param descripe
     * @return
     */
    @Select("SELECT * FROM customer WHERE customerdelete = 0 AND customername LIKE #{name} AND customeridcard LIKE #{idCard} AND customerdescripe LIKE #{descripe}")
    List<Customer> getCustomerByNameOrIdCardOrDescripe(String name, String idCard, String descripe);

    /**
     * 按照id进行删除
     * @param id
     * @return
     */
    @Update("UPDATE customer SET  customerdelete = 1 WHERE customerid = #{id}")
    Integer deleteCustomerById(Integer id);

    /**
     * 按照id进行修改
     * @param id
     * @param date
     * @param name
     * @param idCard
     * @param description
     * @return
     */
    @Update("UPDATE customer SET customerdate = #{date} , " +
            "customername = #{name} ,customeridcard = #{idCard} , " +
            "customerdescripe = #{description} , customerdelete = #{delete} " +
            "WHERE customerid = #{id}")
    Integer updateCustomerSingle(Integer id,Integer delete,Date date,String name,String idCard,String description);
    @Select("SELECT * FROM customer WHERE customerdelete = 0")
    List<Customer> getCustomerList();
}
