package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.util.IdCardUtil;
import com.example.demo.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerMapper customerMapper;
    /*插入单个*/
    public Result insertCustomerSingle(String strDate, String name, String card, String description){
        Date date = null;
        if(strDate == null){
            date = new Date();
        }else{
            try {
                date = StrUtil.stringToDate(strDate);
            }catch (Exception e){
                return Result.getResult(ExceptionEnum.DATA_ERR);
            }
        }
        if(name == null || card == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        String boolCard = IdCardUtil.IDCardValidate(card);
        if(!boolCard.equals("1")){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Customer customer = customerMapper.getCustomerByIdCard(card);
        if(null != customer){
            return Result.getResult(ExceptionEnum.DATA_HAS_EXIST);
        }
        Integer customerCount = customerMapper.insertCustomerSingle(date,name,card,description);
        if(!customerCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    /*查询*/
    public  Customer getCustomerById(Integer id){
        return customerMapper.getCustomerById(id);
    }
    /*模糊查询*/
    public List<Customer> getCustomerByNameOrIdCardOrDescripe(String name, String idCard, String descripe){
        String afterName = StrUtil.formateVager(name);
        String afterIdCard = StrUtil.formateVager(idCard);
        String afterDescription = StrUtil.formateVager(descripe);
        return customerMapper.getCustomerByNameOrIdCardOrDescripe(afterName,afterIdCard,afterDescription);
    }
    /*依据id进行删除*/
    public Result  deleteCustomerById(Integer id){
        Customer customer = customerMapper.getCustomerById(id);
        if(customer == null){
            return Result.getResult(ExceptionEnum.NO_DATA);
        }
        Integer deleteCount = customerMapper.deleteCustomerById(id);
        if(!deleteCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    /*list删除*/
    public Result  deleteCustomerByList(String arr){
        if(arr == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        List<Integer> list;
        try {
            list = JSON.parseArray(arr,Integer.class);
        }catch (Exception e){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        for (int i = 0; i < list.size(); i++) {
            Customer customer = customerMapper.getCustomerById(list.get(i));
            if(customer == null){
                return Result.getResult(ExceptionEnum.NO_DATA);
            }
            Integer deleteCustomerCount = customerMapper.deleteCustomerById(list.get(i));
            if(!deleteCustomerCount.equals(1)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.getResult(ExceptionEnum.OP_ERROR);
            }
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    /*单个修改*/
    public Result updateCustomerSingle(Integer id,Integer delete,String strDate,String name,String idCard,String description){
        Date date = null;
        if(strDate == null){
            date = new Date();
        }else{
            try {
                date = StrUtil.stringToDate(strDate);
            }catch (Exception e){
                return Result.getResult(ExceptionEnum.DATA_ERR);
            }
        }
        if(name == null || idCard == null || delete == null || (!delete.equals(1) && !delete.equals(0))){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        String boolCard = IdCardUtil.IDCardValidate(idCard);
        if(!boolCard.equals("1")){
            return Result.getResult(ExceptionEnum.DATA_ERR,boolCard);
        }
        Integer customerCount = customerMapper.updateCustomerSingle(id,delete,date,name,idCard,description);
        if(!customerCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    public List<Customer> getCustomerList() {
        return customerMapper.getCustomerList();
    }
}
