package com.example.demo.controller;

import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.entity.Trip;
import com.example.demo.service.TripService;
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
public class TripController {
    @Autowired
    TripService tripService;
    //查询所有行程
    @GetMapping("/getAllTrip")
    @ApiOperation(value = "获取所有行程",notes = "获取所有行程")
    public List<Trip> getAllTrip(){
        return tripService.getAllTrip();
    }

    @GetMapping("/getAllTrip/{id}")
    @ApiOperation(value = "获取id对应的行程",notes = "获取id对应的行程")
    public List<Trip> getAllTripById(@PathVariable("id") Integer id){
        return tripService.getAllTripById(id);
    }

    @PostMapping("/getAllTrip/{name}")
    @ApiOperation(value = "获取模糊行程名对应的行程",notes = "获取模糊行程名的行程")
    public List<Trip> getAllTripById(@PathVariable("name") String name){
        name="%"+name+"%";
        return tripService.getTripByVagueName(name);
    }

    @PostMapping("/addTrip")
    @ApiOperation(value = "新增行程",notes = "新增行程")
    public Result addTrip(String name, String strDate, String dedicatedlineid){
        Date date= null;
        try{
            date= StrUtil.stringToDate(strDate);
        }catch (Exception e){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return tripService.insertManyTrip(name,date,dedicatedlineid);
    }

    @PostMapping("/updateTripAll")
    @ApiOperation(value = "仅修改行程单",notes = "修改行程单,一经修改,所有引用都修改,建议新增,后修改专线")
    public Result updateTrip(Integer id,String name, Integer delete,String createDate){
        Date date= null;
        try{
            date= StrUtil.stringToDate(createDate);
        }catch (Exception e){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return tripService.updateTripAll(id,name,delete,date);
    }

    /*@GetMapping("/deleteTripSingle")
    @ApiOperation(value = "依据单个进行删除",notes = "删除专线则专线下的行程也将被删除")
    public Result deleteTripSingle(Integer id){
        return tripService.deleteTripSingle(id);
    }
    @GetMapping("/deleteDedicatedLineList")
    @ApiOperation(value = "依据list进行删除",notes = "删除专线则专线下的行程也将被删除")
    public Result deleteTripList(String arr){
        return tripService.deleteTripSingle(arr);
    }*/
}
