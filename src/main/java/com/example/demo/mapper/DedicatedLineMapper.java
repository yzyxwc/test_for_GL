package com.example.demo.mapper;

import com.example.demo.entity.DedicatedLine;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface DedicatedLineMapper {


    @Select("select * from dedicatedline where dedicatedlineid = #{id} and dedicatedlinedelete = 0")
    @Results({
            @Result(property = "dedicatedlineid", column = "dedicatedlineid"),
            @Result(property = "trip",column = "dedicatedlineid",javaType = List.class,many = @Many(select = "com.example.demo.mapper.TripMapper.selectTripByDedicatedLineId"))
    })
    DedicatedLine selectDedicatedLineById(Integer id);
    @Select("select * from dedicatedline where dedicatedlineid = #{id} and dedicatedlinedelete = 0")
    DedicatedLine selectTripDedicatedLineById(Integer id);
    @Select("select * from dedicatedline")
    List<DedicatedLine> selectDedicatedLine();
    @Select("select * from dedicatedline where dedicatedlinename like #{name} and dedicatedlinedelete = 0")
    @Results({
            @Result(property = "dedicatedlineid" ,column = "dedicatedlineid"),
            @Result(property = "trip",column = "dedicatedlineid",javaType = List.class,many = @Many(select = "com.example.demo.mapper.TripMapper.selectTripByDedicatedLineId"))
    })
    DedicatedLine selectDedicatedLineVagueByName(String name);
    @Insert("insert into dedicatedline(dedicatedlinename,createdate) values(#{name},#{date})")
    Integer DedicatedLine(Date date, String name);
    /*@Select("select * from dedicatedline where dedicatedlinename = #{name} and createdate = #{date} and dedicatedlinedelete = 0")
    DedicatedLine getDedicatedLineByDateAndName(Date date, String name);*/
    @Select("select * from dedicatedline where dedicatedlinename = #{name} and dedicatedlinedelete = 0")
    DedicatedLine getDedicatedLineByName(String name);
    @Update("update dedicatedline " +
            "set " +
            "createdate = #{date} ," +
            "dedicatedlinedelete = #{dedicatedlinedelete} , " +
            "dedicatedlinename = #{dedicatedlinename} " +
            "where " +
            "dedicatedlineid = #{dedicatedlineid}")
    Integer updateDedicatedLineSingle(Integer dedicatedlineid, Date date, Integer dedicatedlinedelete, String dedicatedlinename);
    @Update("update dedicatedline set dedicatedlinedelete=1 where  dedicatedlineid = #{id}")
    Integer deleteDedicatedLineById(Integer id);
}