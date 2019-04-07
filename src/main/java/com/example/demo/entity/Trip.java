package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 行程_专线
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private Integer tripid;
    private Date createdate;
    private Boolean tripdelete;
    private TripAll tripallid;
    private DedicatedLine dedicatedlineid;
}
