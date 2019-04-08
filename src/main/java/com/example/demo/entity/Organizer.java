package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织者
 * 一个订单对应一个组织者
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {
   private Integer organizerid;
   private Integer organizerdelete;
   private String organizername;
   private String organizerdescripe;
}
