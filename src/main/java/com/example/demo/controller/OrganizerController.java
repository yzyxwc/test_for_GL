package com.example.demo.controller;

import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.service.OrganizerService;
import com.example.demo.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class OrganizerController {
    @Autowired
    OrganizerService organizerService;

    @PostMapping("/addOrganizer")
    @ApiOperation(value = "新增组织者",notes = "新增组织者")
    public Result addOrganizer(String name, String organizerdescripe){
        return organizerService.insertOrganizerSingle(name,organizerdescripe);
    }
}
