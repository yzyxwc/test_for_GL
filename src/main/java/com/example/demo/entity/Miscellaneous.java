package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 杂项支出
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Miscellaneous {
    private Integer miscellaneousid;
    private Date createdate;
    private Integer miscellaneousdelete;
    /**
     * 支出
     */
    private BigDecimal cost;
    private String description;

    public Miscellaneous(Date createdate, BigDecimal cost, String description) {
        this.createdate = createdate;
        this.cost = cost;
        this.description = description;
    }
}
