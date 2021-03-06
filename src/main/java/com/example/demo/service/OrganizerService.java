package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.Result;
import com.example.demo.mapper.OrganizerMapper;
import com.example.demo.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Transactional
public class OrganizerService {
    @Autowired
    OrganizerMapper organizerMapper;
    //按照名字和描述插入唯一匹配Organizer
    public Result insertOrganizerSingle(String name, String organizerdescripe) {
        if(name == null || organizerdescripe==null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Organizer orgernizer=organizerMapper.selectOrganizerByNameAndDescripe(name,organizerdescripe);
        if(orgernizer !=null){
            return Result.getResult(ExceptionEnum.DATA_HAS_EXIST);
        }
         Integer addOrganizerCount = organizerMapper.insertOrganizerSingle(name,organizerdescripe);
         if(!addOrganizerCount.equals(1)){
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             return Result.getResult(ExceptionEnum.OP_ERROR);
         }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    //id查询Organizer
    public Organizer selectOrganizerById(Integer id){
        if (id == null){
            return null;
        }
        Organizer organizer=organizerMapper.selectOrganizerById(id);
        return organizer;
    }
    //按照名字和描述查找唯一匹配Organizer
    public Organizer selectOrganizerByNameAndDescripe(String name, String organizerdescripe){
        if (name == null){
            return null;
        }
        Organizer organizer=organizerMapper.selectOrganizerByNameAndDescripe(name,organizerdescripe);
        return organizer;
    }
    //名字和描述进行模糊搜索
    public Result selectVagueOrganizerByNameAndDescripe(String name, String organizerdescripe, int page, Integer size){
        String orgnizeName = StrUtil.formateVager(name);
        String descripe = StrUtil.formateVager(organizerdescripe);
        int total = organizerMapper.selectVagueOrganizerByNameAndDescripe(orgnizeName, descripe).size();
        List<Organizer> organizers = organizerMapper.selectVagueOrganizerByNameAndDescripeList(orgnizeName, descripe,page*size,size);
        return Result.getResult(ExceptionEnum.OP_SUCCESS,organizers,total);
    }
    //查询所有Organizer
    public Result selectAllOrganizer(int page, Integer size){
        List<Organizer> organizer = organizerMapper.selectAllOrganizerList(page*size,size);
        int total =  organizerMapper.selectAllOrganizer().size();
        return Result.getResult(ExceptionEnum.OP_SUCCESS,organizer,total);
    }
    //按照id修改Orgernizer
    public Result updateOrganizerById(Integer id,Integer delete,String name, String organizerdescripe){
        if(delete == null || (!delete.equals(1) && !delete.equals(0)) || name == null || organizerdescripe == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        Integer updateCount = organizerMapper.updateOrganizerById(id,delete,name,organizerdescripe);
        if(!updateCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }

    //按照id 删除
    public Result deleteOrgernizerById(Integer id){
        if(id == null){
            return Result.getResult(ExceptionEnum.DATA_ERR);
        }
        Integer orgernizerCount = organizerMapper.deleteorgernizerById(id);
        if(!orgernizerCount.equals(1)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
    public Result deleteOrgernizerByList(String arr){
        if(arr == null){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        List<Integer> list = JSON.parseArray(arr,Integer.class);
        for (int i = 0; i < list.size(); i++) {
            Result result = this.deleteOrgernizerById(list.get(i));
            if(result.getCode() != 1000){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.getResult(ExceptionEnum.OP_ERROR);
            }
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
}
