package com.example.demo.mapper;

import com.example.demo.entity.DedicatedLine;
import com.example.demo.entity.Trip;
import com.example.demo.entity.TripAll;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface TripMapper {
    @Select("select * from trip where tripdelete=0 order by createdate desc")
    @Results({
            @Result(property = "tripallid", column = "tripallid", javaType = TripAll.class, one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "dedicatedlineid", column = "dedicatedlineid", javaType = DedicatedLine.class,jdbcType = JdbcType.INTEGER , one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById"))
    })
    List<Trip> getAllTrip();
    @Select("select * from trip where tripdelete=0 and dedicatedlineid = #{id}")
    @Results({
            @Result(property = "tripallid", column = "tripallid", javaType = TripAll.class, one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "dedicatedlineid", column = "dedicatedlineid", javaType = DedicatedLine.class, one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectDedicatedLineById"))
    })
    List<Trip> selectTripByDedicatedLineId(Integer id);
    @Select("select * from trip where tripdelete = 0 and tripallid=#{id} order by createdate desc")
    @Results({
            @Result(property = "tripallid", column = "tripallid", javaType = TripAll.class, one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "dedicatedlineid", column = "dedicatedlineid", javaType = DedicatedLine.class, one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById"))
    })
    List<Trip> getAllTripById(Integer id);
    @Select("SELECT t.* from trip t,tripall ta where t.tripdelete=0 and t.tripallid=ta.tripallid " +
            "and ta.tripallname like #{name} order by t.createdate desc")
    @Results({
            @Result(property = "tripallid", column = "tripallid", javaType = TripAll.class, one = @One(select = "com.example.demo.mapper.TripAllMapper.getTripAllById")),
            @Result(property = "dedicatedlineid", column = "dedicatedlineid", javaType = DedicatedLine.class, one = @One(select = "com.example.demo.mapper.DedicatedLineMapper.selectTripDedicatedLineById"))
    })
    List<Trip> getTripByVagueName(String name);
    /*插入数据*/
    @Insert("insert into trip(createdate,tripallid,dedicatedlineid)values(#{date},#{tripallid},#{dedicatedlineid})")
    Integer insertSingleTrip(Integer tripallid , Date date,Integer dedicatedlineid);
    @Insert("insert into trip(createdate,tripallid)values(#{date},#{name})")
    Integer insertSingleTripWithoutDedicated(Integer name , Date date);
    /*修改数据*/
    @Update("update tripall set  tripallname = #{name} , tripalldelete =#{delete} ,tripallcreatedate = #{date} where tripallid = #{id}")
    Integer updateSingleTrip(Integer id, String name, Integer delete, Date date);
    /*删除数据*/
    @Update("update trip set tripdelete=1 where  dedicatedlineid = #{id}")
    Integer deletTripByDedicatedLineId(Integer id);
    @Update("update trip set tripdelete=1 where  tripallid = #{id}")
    Integer deleteTripByTripAllId(Integer id);
}
