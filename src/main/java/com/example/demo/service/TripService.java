package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.entity.Trip;
import com.example.demo.entity.TripAll;
import com.example.demo.mapper.TripAllMapper;
import com.example.demo.mapper.TripMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

@Service
//@Slf4j
public class TripService {
    @Autowired
    TripMapper tripMapper;
    @Autowired
    TripAllMapper tripAllMapper;
    public List<Trip> getAllTrip() {
        List<Trip> trip = tripMapper.getAllTrip();
        //log.debug("查询所有行程:",trip);
        return trip;
    }

    public List<Trip> getAllTripById(Integer id) {
        List<Trip> trip = tripMapper.getAllTripById(id);
        //log.debug("按照id查询行程:",trip);
        return trip;
    }

    public List<Trip> getTripByVagueName(String name) {
        List<Trip> trip = tripMapper.getTripByVagueName(name);
        //log.debug("按照name模糊查询行程:",trip);
        return trip;
    }
    //一条一条的插入trip表
    public Result insertSingleTrip(String name , Date date, Integer dedicatedlineid){
        //新增行程
        Integer singleTripAll = tripAllMapper.insertSingleTrip(name,date);
        if(!singleTripAll.equals(1)){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer tripallid=tripAllMapper.getTripAllByNameAndDate(name,date).getTripallid();
        Integer singleTrip = 0;
        if(dedicatedlineid != null){
            singleTrip = tripMapper.insertSingleTrip(tripallid,date,dedicatedlineid);
        }else{
            singleTrip = tripMapper.insertSingleTripWithoutDedicated(tripallid,date);
        }

        if(!singleTrip.equals(1)){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    //保留设计 select2插入dedicatedlineid
    public Result insertManyTrip(String name , Date date, String dedicatedlineid){
        if(name == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        if(date == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        TripAll tripall = tripAllMapper.getTripAllByName(name);
        if(tripall != null){
            return Result.getResult(ExceptionEnum.DATA_HAS_EXIST);
        }
        Integer addCount = tripAllMapper.insertSingleTrip(name,date);
        if(!addCount.equals(1)){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        TripAll tripentity=tripAllMapper.getTripAllByName(name);
        if(tripentity == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        List<Integer> list= JSON.parseArray(dedicatedlineid,Integer.class);
        if(list != null && list.size() != 0){
            for (int i = 0; i < list.size(); i++) {
                Integer insertTripAll = tripMapper.insertSingleTrip(tripentity.getTripallid(), date, list.get(i));
                if(!insertTripAll.equals(1)){
                    return Result.getResult(ExceptionEnum.OP_ERROR);
                }
            }
        }else {
            tripMapper.insertSingleTripWithoutDedicated(tripentity.getTripallid(),date);
        }
    return  Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
//修改行程
    public Result updateTripAll(Integer id, String name, Integer delete, Date date) {
        if(name == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        if(delete == null || (!delete.equals(1) && !delete.equals(0))){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer updatetrip = tripMapper.updateSingleTrip(id,name,delete,date);
        if(!updatetrip.equals(1)){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return  Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    public Result deleteTripSingle(Integer id) {
        if(id == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer tripAllCount = tripAllMapper.deleteTripById(id);
        Integer tripCount = tripMapper.deleteTripByTripAllId(id);
        if(!tripAllCount.equals(1) || tripCount == null || tripCount<0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    public Result deleteTripList(String arr) {
        if(arr != null) {
            List<Integer> list = JSON.parseArray(arr,Integer.class);
            for (int i = 0; i < list.size(); i++) {
                Result result = this.deleteTripSingle(list.get(i));
                if(result.getCode()!=1000){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.getResult(ExceptionEnum.OP_ERROR);
                }
            }
        }else{
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
}
