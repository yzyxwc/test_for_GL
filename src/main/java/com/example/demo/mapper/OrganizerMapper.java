package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganizerMapper {
    @Insert("INSERT INTO organizer(organizername ,organizerdescripe) " +
            "VALUES(#{name},#{organizerdescripe})")
    Integer insertOrganizerSingle(String name, String organizerdescripe);
}
