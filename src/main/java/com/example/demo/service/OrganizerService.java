package com.example.demo.service;

import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.mapper.OrganizerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class OrganizerService {
    @Autowired
    OrganizerMapper organizerMapper;
    public Result insertOrganizerSingle(String name, String organizerdescripe) {
        if(name == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
         Integer addOrganizerCount = organizerMapper.insertOrganizerSingle(name,organizerdescripe);
         if(!addOrganizerCount.equals(1)){
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             return Result.getResult(ExceptionEnum.OP_ERROR);
         }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
}
