package com.example.demo.service;

import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Miscellaneous;
import com.example.demo.entity.Result;
import com.example.demo.mapper.MiscellaneousMapper;
import com.example.demo.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MiscellaneousService {
    @Autowired
    MiscellaneousMapper miscellaneousMapper;
    public Result insertMiscellaneousSingle(String strcreatedate,String strcost,String strdescription){
        if(strcreatedate == null || strcost == null ){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Date  createdate = null;
        BigDecimal cost ;
        String description = strdescription==null?"":strdescription;
        try {
            createdate = StrUtil.stringToDate(strcreatedate);
            cost = StrUtil.stringToBigDecimal(strcost);
        }catch (Exception e){
            return Result.getResult(ExceptionEnum.FORMATTER_ERR_DATA);
        }
        Miscellaneous miscellaneous = new Miscellaneous(createdate,cost,description);
        Integer count = miscellaneousMapper.insertMiscellaneousSingle(miscellaneous);
        if(!count.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    //获取所有
     public Result getAllMiscellaneous(){
         List<Miscellaneous> list = miscellaneousMapper.getAllMiscellaneous();
         if(list == null){
             return Result.getResult(ExceptionEnum.NO_DATA);
         }
         return Result.getResult(ExceptionEnum.OP_SUCCESS,list);
     }
     //按照id获取
     public Result  getAllMiscellaneousById(Integer id){
         Miscellaneous miscellaneous = miscellaneousMapper.getAllMiscellaneousById(id);
         if(miscellaneous == null){
             return Result.getResult(ExceptionEnum.NO_DATA);
         }
         return Result.getResult(ExceptionEnum.OP_SUCCESS,miscellaneous);
     }
    //按照id删除
    public Result  deleteMiscellaneousById(Integer id){
        Miscellaneous miscellaneous = miscellaneousMapper.getAllMiscellaneousById(id);
        if(miscellaneous == null){
            return Result.getResult(ExceptionEnum.NO_DATA);
        }
        Integer miscellaneousCount = miscellaneousMapper.deleteMiscellaneousById(id);
        if(!miscellaneousCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_NODATA);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    public Result updateMiscellaneousById(Integer id,Integer delete,String strcreatedate,String strcost,String strdescription){
        if(delete.equals(-1) || strcreatedate == null || strcost == null ||id == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Date  createdate = null;
        BigDecimal cost ;
        String description = strdescription==null?"":strdescription;
        try {
            createdate = StrUtil.stringToDate(strcreatedate);
            cost = StrUtil.stringToBigDecimal(strcost);
        }catch (Exception e){
            return Result.getResult(ExceptionEnum.FORMATTER_ERR_DATA);
        }
        Miscellaneous miscellaneousObject = miscellaneousMapper.getAllMiscellaneousById(id);
        if(miscellaneousObject == null){
            return Result.getResult(ExceptionEnum.NO_DATA);
        }
        Miscellaneous miscellaneous = new Miscellaneous(id,createdate,delete,cost,description);
        Integer count = miscellaneousMapper.updateMiscellaneousById(miscellaneous);
        if(!count.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    public Result findMiscellaneousVague(String month) {
        if(null == month){
            month = LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue();
        }

        List<String> list = StrUtil.getPeridTime(month);
        List<Miscellaneous> allMiscellaneousByMonth = miscellaneousMapper.getAllMiscellaneousByMonth(list.get(0), list.get(1));
        return Result.getResult(allMiscellaneousByMonth);
    }
}
