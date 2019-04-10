package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.service.MiscellaneousService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiscellaneousController {
    @Autowired
    MiscellaneousService miscellaneousService;
    @GetMapping("/getAllMiscellaneous")
    @ApiOperation(value = "获得所有杂项",notes = "获得所有杂项")
    public Result getAllMiscellaneous(){
        return miscellaneousService.getAllMiscellaneous();
    }
    @GetMapping("/getAllMiscellaneousById/{id}")
    @ApiOperation(value = "获得id对应的杂项",notes = "获得id对应的杂项")
    public Result getAllMiscellaneousById(@PathVariable("id") Integer id){
        return miscellaneousService.getAllMiscellaneousById(id);
    }
    @DeleteMapping("/deleteMiscellaneousById/{id}")
    @ApiOperation(value = "删除id对应的杂项",notes = "删除id对应的杂项")
    public Result deleteMiscellaneousById(@PathVariable("id") Integer id){
        return miscellaneousService.deleteMiscellaneousById(id);
    }
    @PostMapping("/updateMiscellaneousById")
    @ApiOperation(value = "修改id对应的杂项",notes = "修改id对应的杂项")
    public  Result updateMiscellaneousById(Integer id,Integer delete,String strcreatedate,String strcost,String strdescription){
        return miscellaneousService.updateMiscellaneousById(id,delete,strcreatedate,strcost,strdescription);
    }
    @PostMapping("/insertMiscellaneousSingle")
    @ApiOperation(value = "插入杂项",notes = "插入杂项")
    public Result insertMiscellaneousSingle(String strcreatedate,String strcost,String strdescription){
        return miscellaneousService.insertMiscellaneousSingle(strcreatedate,strcost,strdescription);
    }
}
