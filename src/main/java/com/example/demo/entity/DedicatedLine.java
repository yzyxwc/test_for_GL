package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 专线(公司)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DedicatedLine {
    private Integer dedicatedlineid;
    private Date createdate;
    private String dedicatedlinename;
    private Boolean dedicatedlinedelete;
    private List<TripAll> trip;
}
