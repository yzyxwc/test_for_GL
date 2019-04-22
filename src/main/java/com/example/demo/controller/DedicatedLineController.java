package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.DedicatedLine;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.entity.Trip;
import com.example.demo.service.DedicatedLineService;
import com.example.demo.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class DedicatedLineController {
    @Autowired
    DedicatedLineService dedicatedLineService;
    @GetMapping("/dedicatedLineById/{id}")
    @ApiOperation(value = "按照id获取专线及专线下的行程",notes = "按照id获取专线及专线下的行程")
    public DedicatedLine selectDedicatedLineById(@PathVariable("id") Integer id){
        return dedicatedLineService.selectDedicatedLineById(id);
    }

    @GetMapping("/getAllDedicatedLine")
    @ApiOperation(value = "获取所有专线",notes = "获取所有专线")
    public List<DedicatedLine> selectDedicatedLine(){
        return dedicatedLineService.selectDedicatedLine();
    }

    @PostMapping("/dedicatedLineById/{name}")
    @ApiOperation(value = "按照专线名模糊搜索",notes = "按照专线名模糊搜索")
    public DedicatedLine selectDedicatedLineById(@PathVariable("name") String name){
        name="%"+name+"%";
        return dedicatedLineService.selectDedicatedLineVagueByName(name);
    }

    @PostMapping("/addDedicatedLine")
    @ApiOperation(value = "新增专线",notes = "新增专线")
    public Result addDedicatedLine(String name, String tripallid, String tripName){
        List<Integer> demoList = JSON.parseArray(tripallid,Integer.class);
        Date date=new Date();
        return dedicatedLineService.addDedicatedLine(date,name,demoList,tripName);
    }

    @PostMapping("/updateDedicatedLineSingle")
    @ApiOperation(value = "updateDedicatedLineSingle",notes = "修改单个专线")
    public Result updateDedicatedLineSingle(Integer dedicatedlineid,String createdate,Integer dedicatedlinedelete, String dedicatedlinename, String tripAllid){
        List<Integer> demoList = JSON.parseArray(tripAllid,Integer.class);
        Date date= null;
        try{
            date= StrUtil.stringToDate(createdate);
        }catch (Exception e){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return dedicatedLineService.updateDedicatedLine(dedicatedlineid,date,dedicatedlinedelete,dedicatedlinename,demoList);
    }

    @GetMapping("/deleteDedicatedLineSingle")
    @ApiOperation(value = "依据单个进行删除",notes = "删除专线则专线下的行程也将被删除")
    public Result deleteDedicatedLineSingle(Integer id){
        return dedicatedLineService.deleteDedicatedLineSingle(id);
    }
    @GetMapping("/deleteDedicatedLineList")
    @ApiOperation(value = "依据list进行删除",notes = "删除专线则专线下的行程也将被删除")
    public Result deleteDedicatedLineList(String arr){
        return dedicatedLineService.deleteDedicatedLineList(arr);
    }
    //增加删除专线下的行程

}
