package com.example.demo.mapper;

import com.example.demo.entity.Miscellaneous;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MiscellaneousMapper {
    @Insert("INSERT INTO miscellaneous(createdate,cost,description)" +
            "VALUES(#{createdate},#{cost},#{description})")
    Integer insertMiscellaneousSingle(Miscellaneous miscellaneous);
    @Select("SELECT * FROM miscellaneous WHERE miscellaneousdelete = 0")
    List<Miscellaneous> getAllMiscellaneous();
    @Select("SELECT * FROM miscellaneous WHERE miscellaneousdelete = 0 " +
            "AND miscellaneousid = #{id}")
    Miscellaneous   getAllMiscellaneousById(Integer id);
    @Update("UPDATE miscellaneous SET miscellaneousdelete = 1 WHERE miscellaneousid = #{id}")
    Integer deleteMiscellaneousById(Integer id);
    @Update("UPDATE miscellaneous SET " +
            "miscellaneousdelete = #{miscellaneousdelete},createdate = #{createdate} , " +
            "cost = #{cost} ,description = #{description} " +
            "WHERE miscellaneousid = #{miscellaneousid}")
    Integer updateMiscellaneousById(Miscellaneous miscellaneous);
}
