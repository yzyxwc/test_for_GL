package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 行程
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripAll {
    private Integer tripallid;
    private Date tripallcreatedate;
    private Boolean tripalldelete;
    private String tripallname;
}
