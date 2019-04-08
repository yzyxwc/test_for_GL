package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where name = #{name}")
    User login(String name);
    @Update("update user set id = #{id},name = #{name},password = #{password},token = #{token} where id = #{id}")
    int updateUser(User user);
    @Select("select * from user where token = #{token}")
    User getUserByToken(String token);
    @Update("update user set token = '' where id = #{id}")
    Integer layout(Integer id);
}
