package com.example.demo.mapper;

import com.example.demo.entity.Organizer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrganizerMapper {
    @Insert("INSERT INTO organizer(organizername ,organizerdescripe) " +
            "VALUES(#{name},#{organizerdescripe})")
    Integer insertOrganizerSingle(String name, String organizerdescripe);
    @Select("SELECT * FROM organizer " +
            "WHERE organizername=#{name} " +
            "AND organizerdescripe = #{organizerdescripe} " +
            "AND organizerdelete = 0")
    Organizer selectOrganizerByNameAndDescripe(String name, String organizerdescripe);
    @Select("SELECT * FROM organizer " +
            "WHERE organizerid=#{id} AND organizerdelete = 0")
    Organizer selectOrganizerById(Integer id);
    @Select("SELECT * FROM organizer " +
            "WHERE organizername LIKE #{name} " +
            "AND organizerdescripe LIKE #{organizerdescripe} AND organizerdelete = 0")
    List<Organizer> selectVagueOrganizerByNameAndDescripe(String name, String organizerdescripe);
    @Select("SELECT * FROM organizer WHERE organizerdelete = 0")
    List<Organizer> selectAllOrganizer();

    /*订单号查询组织者信息*/
    @Select("SELECT * FROM organizer " +
            "WHERE organizerdelete = 0 AND organizerid IN " +
            "(SELECT orgernizerid FROM order WHERE orderid = #{id}) ")
    Organizer selectOrganizerByOrderId(Integer id);
    @Update("UPDATE organizer SET organizerdelete = #{delete}, " +
            "organizername=#{name} , " +
            "organizerdescripe = #{organizerdescripe} " +
            "WHERE organizerid=#{id}")
    Integer updateOrganizerById(Integer id,Integer delete,String name, String organizerdescripe);
    @Update("UPDATE organizer SET organizerdelete = 1 WHERE  organizerid=#{id}")
    Integer deleteorgernizerById(Integer id);
    @Select("SELECT * FROM organizer WHERE organizerdelete = 0 limit #{start},#{size}")
    List<Organizer> selectAllOrganizerList(int start, Integer size);
    @Select("SELECT * FROM organizer " +
            "WHERE organizername LIKE #{orgnizeName} " +
            "AND organizerdescripe LIKE #{descripe} AND organizerdelete = 0 limit #{start},#{size}")
    List<Organizer> selectVagueOrganizerByNameAndDescripeList(String orgnizeName, String descripe, int start, Integer size);
}
