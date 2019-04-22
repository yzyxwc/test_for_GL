package com.example.demo.service;

import com.example.demo.entity.CustomerOrder;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.mapper.CustomerOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class CustomerOrderService {
    @Autowired
    CustomerOrderMapper customerOrderMapper;
    public Result insertCustomerOrderSingle(Integer orderId, Integer customerId) {
        if(orderId == null || customerId == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        CustomerOrder customerOrder = customerOrderMapper.selectCustomerOrderByOrderIdAndCustomerId(orderId,customerId);
        if(null != customerOrder){
            return Result.getResult(ExceptionEnum.DATA_HAS_EXIST);
        }
        CustomerOrder customerOrderAdd = new CustomerOrder(customerId , orderId);
        Integer integer = customerOrderMapper.insertCustomerOrderSingle(customerOrderAdd);
        if(!integer.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer integer1 = customerOrderMapper.updateOrderByCountPeople(orderId);
        /*if(!integer1.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }*/
        return Result.getResult(ExceptionEnum.OP_SUCCESS,customerOrderAdd);

    }

    public Result deleteCustomerOrderSingle(Integer orderId, Integer customerId) {
        if(orderId == null || customerId == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Integer integer = customerOrderMapper.deleteCustomerOrderByOderIdAndCustomerId(orderId,customerId);
        if(!integer.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer integer1 = customerOrderMapper.updateOrderByCountPeople(orderId);
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
}
