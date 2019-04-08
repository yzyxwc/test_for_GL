package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.*;
import com.example.demo.mapper.DedicatedLineMapper;
import com.example.demo.mapper.TripAllMapper;
import com.example.demo.mapper.TripMapper;
import com.example.demo.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

@Service
//@Slf4j
@Transactional
public class DedicatedLineService {
    @Autowired
    DedicatedLineMapper dedicatedLineMapper;
    @Autowired
    TripMapper tripMapper;
    @Autowired
    TripAllMapper tripAllMapper;
    @Autowired
    DedicatedLineService dedicatedLineService;
    public List<Trip> selectDedicatedLineById(Integer id){
        //依据专线id查询专线及该专线下的行程
        List<Trip> dedicatedLine=tripMapper.selectTripByDedicatedLineId(id);
        //log.debug("按照id查询专线id-"+id+":",dedicatedLine);
        return dedicatedLine;
    }
    //查询所有专线
    public List<DedicatedLine> selectDedicatedLine(){
        List<DedicatedLine>  dedicatedLine=dedicatedLineMapper.selectDedicatedLine();
        //log.debug("查询所有专线",dedicatedLine);
        return dedicatedLine;
    }
    //依据专线名模糊搜索专线
    public DedicatedLine selectDedicatedLineVagueByName(String priname) {
        String name = StrUtil.formateVager(priname);
        return  dedicatedLineMapper.selectDedicatedLineVagueByName(name);
       // log.debug("按照name模糊查询专线name="+name+":",dedicatedLine);

    }
    public Result addDedicatedLine(Date date, String name,List<Integer> tripallid,String tripName) {
        if(name == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        DedicatedLine dedicatedLineSingle=dedicatedLineMapper.getDedicatedLineByName(name);
        if(dedicatedLineSingle != null){
            return Result.getResult(ExceptionEnum.DATA_HAS_EXIST);
        }
        Integer addCount=dedicatedLineMapper.DedicatedLine(date,name);
        if(!addCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        DedicatedLine dedicatedLine = dedicatedLineMapper.getDedicatedLineByName(name);
        if(dedicatedLine == null){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        if(tripallid != null && tripallid.size() != 0){
            for (int i = 0; i < tripallid.size(); i++) {
                Integer addtrip = tripMapper.insertSingleTrip(tripallid.get(i),date,dedicatedLine.getDedicatedlineid());
                if(!addtrip.equals(1)){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.getResult(ExceptionEnum.OP_ERROR);
                }
            }
        }
        if(tripName != null){
            TripAll ta=tripAllMapper.getTripAllByName(tripName);
            Integer addtripall = 0;
            if(ta != null){
                addtripall =tripMapper.insertSingleTrip(ta.getTripallid(),date,dedicatedLine.getDedicatedlineid());
            }else {
                addtripall = tripAllMapper.insertSingleTrip(tripName,date);
                TripAll tripall = tripAllMapper.getTripAllByName(tripName);
                if(tripall == null){
                    return Result.getResult(ExceptionEnum.OP_ERROR);
                }
                tripMapper.insertSingleTrip(tripall.getTripallid(),date,dedicatedLine.getDedicatedlineid());
            }
            if(!addtripall.equals(1)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.getResult(ExceptionEnum.OP_ERROR);
            }

        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    public Result updateDedicatedLine(Integer dedicatedlineid, Date date, Integer dedicatedlinedelete, String dedicatedlinename, List<Integer> demoList) {
        if(dedicatedlinename == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        if(dedicatedlinedelete == null || (!dedicatedlinedelete.equals(1) && !dedicatedlinedelete.equals(0))){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Integer dedicatedline=dedicatedLineMapper.updateDedicatedLineSingle(dedicatedlineid,date,dedicatedlinedelete,dedicatedlinename);
        if(!dedicatedline.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        if(demoList != null && demoList.size() !=0 ){
            //先逻辑删除
            Integer deleteCount=tripMapper.deletTripByDedicatedLineId(dedicatedlineid);
            if(deleteCount.equals(0)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.getResult(ExceptionEnum.OP_ERROR);
            }
            for (int i = 0; i < demoList.size(); i++) {
                Integer tripUpdate=tripMapper.insertSingleTrip(demoList.get(i),date,dedicatedlineid);
                if(!tripUpdate.equals(1)){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.getResult(ExceptionEnum.OP_ERROR);
                }
            }
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
//依据id进行逻辑删除
    public Result deleteDedicatedLineSingle(Integer id) {
        if(id == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Integer dedicatedLineCount = dedicatedLineMapper.deleteDedicatedLineById(id);
        Integer tripCount = tripMapper.deletTripByDedicatedLineId(id);
        if(!dedicatedLineCount.equals(1) || tripCount == null || tripCount < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
//依据list进行逻辑删除
    public Result deleteDedicatedLineList(String arr) {
        if(arr != null) {
            List<Integer> list = JSON.parseArray(arr,Integer.class);
            for (int i = 0; i < list.size(); i++) {
                Result result = this.deleteDedicatedLineSingle(list.get(i));
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
