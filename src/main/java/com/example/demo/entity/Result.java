package com.example.demo.entity;

import com.example.demo.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回数据形式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    int code;
    String msg;
    Object data;
    Integer total;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result getResult(){
        return getResult(ExceptionEnum.OP_ERROR);
    }

    public static Result getResult(Object data){
        if(data == null){
            return getResult(ExceptionEnum.OP_NODATA);
        }else{
            return getResult(ExceptionEnum.OP_SUCCESS,data);
        }
    }
    public static Result getResult(ExceptionEnum code, Object data,Object ...args){
        String msg = code.getMsg();
        Result result = new Result();
        if(!StrUtil.isEmpty(msg)){
            result.setCode(code.getCode());
            result.setMsg(String.format(code.getMsg(),args));
        }
        if (data != null){
            result.setData(data);
        }
        return result;
    }
    public static Result result(Integer code, String message,Object data){
        return new Result(code,message,data);
    }
    public static Result getResult(ExceptionEnum code){
        return getResult(code ,null);
    }
    public static Result error(ExceptionEnum code){
        return error(code.getCode(),code.getMsg());
    }
    public static Result error(ExceptionEnum code,Object data){
        return error(code.getCode(),code.getMsg(),data);
    }
    public static Result error(Integer code, String message) {
        return result(code,message,null);
    }
    public static Result error(Integer code, String message,Object data) {
        return result(code,message,data);
    }
    public static Result success() {
        return getResult(ExceptionEnum.OP_SUCCESS);
    }
    public static Result success(Object obj){
        return getResult(ExceptionEnum.OP_SUCCESS,obj);
    }

    public static Result getResult(ExceptionEnum code,Object data, int size) {
        String msg = code.getMsg();
        Result result = new Result();
        if(!StrUtil.isEmpty(msg)){
            result.setCode(code.getCode());
            result.setMsg(msg);
            result.setTotal(size);
        }
        if (data != null){
            result.setData(data);
        }
        return result;
    }
}
