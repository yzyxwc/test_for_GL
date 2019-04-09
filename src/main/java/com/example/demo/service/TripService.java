package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.entity.Trip;
import com.example.demo.entity.TripAll;
import com.example.demo.mapper.TripAllMapper;
import com.example.demo.mapper.TripMapper;
import com.example.demo.util.StrUtil;
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
        String afterName = StrUtil.formateVager(name);
        List<Trip> trip = tripMapper.getTripByVagueName(afterName);
        //log.debug("按照name模糊查询行程:",trip);
        return trip;
    }
    //一条一条的插入trip表
    public Result insertSingleTrip(String tripallname , Date tripallcreatedate, Integer dedicatedlineid){
        //新增行程
        TripAll tripAll = new TripAll(tripallname,tripallcreatedate);
        Integer singleTripAll = tripAllMapper.insertObjectTrip(tripAll);
        //Integer singleTripAll = tripAllMapper.insertSingleTrip(tripallname,tripallcreatedate);
        if(!singleTripAll.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer tripallid=tripAll.getTripallid();
        Integer singleTrip = 0;
        if(dedicatedlineid != null){
            singleTrip = tripMapper.insertSingleTrip(tripallid,tripallcreatedate,dedicatedlineid);
        }else{
            singleTrip = tripMapper.insertSingleTripWithoutDedicated(tripallid,tripallcreatedate);
        }

        if(!singleTrip.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    //保留设计 select2插入dedicatedlineid
    public Result insertManyTrip(String tripallname , Date tripallcreatedate, String dedicatedlineid){
        if(tripallname == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        if(tripallcreatedate == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        TripAll tripall = tripAllMapper.getTripAllByName(tripallname);
        if(tripall != null){
            return Result.getResult(ExceptionEnum.DATA_HAS_EXIST);
        }
        TripAll tripAll = new TripAll(tripallname,tripallcreatedate);
        Integer singleTripAll = tripAllMapper.insertObjectTrip(tripAll);
        if(!singleTripAll.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        List<Integer> list = null;
        try {
            list = JSON.parseArray(dedicatedlineid, Integer.class);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        if(list != null && list.size() != 0){
            for (int i = 0; i < list.size(); i++) {
                Integer insertTripAll = tripMapper.insertSingleTrip(tripAll.getTripallid(), tripallcreatedate, list.get(i));
                if(!insertTripAll.equals(1)){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.getResult(ExceptionEnum.OP_ERROR);
                }
            }
        }else {
            tripMapper.insertSingleTripWithoutDedicated(tripAll.getTripallid(),tripallcreatedate);
        }
    return  Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
//修改行程
    public Result updateTripAll(Integer id, String name, Integer delete, Date date) {
        if(name == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        if(delete == null || (!delete.equals(1) && !delete.equals(0))){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Integer updatetrip = tripMapper.updateSingleTrip(id,name,delete,date);
        if(!updatetrip.equals(1)){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return  Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    public Result deleteTripSingle(Integer id) {
        if(id == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
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
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
}
