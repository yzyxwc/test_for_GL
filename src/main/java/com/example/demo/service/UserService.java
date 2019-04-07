package com.example.demo.service;

import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public Result login(String name, String password){
        User user=userMapper.login(name);
        if(user == null){
            return Result.getResult(ExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        if(!password.equals(user.getPassword())){
            return Result.getResult(ExceptionEnum.PASSWORD_ERROR);
        }
        user.setPassword("");
        Long time=System.currentTimeMillis();
        User tokenUser=null;
        try {
            String token= StrUtil.Md5(time+name);
            StrUtil.token=token;
            user.setToken(token);
            tokenUser=new User(user.getId(),user.getName(),password,token);
        }catch (Exception e){
            return  Result.getResult(ExceptionEnum.OP_ERROR);
        }
       Integer updateUserCount= userMapper.updateUser(tokenUser);
       if(!updateUserCount.equals(1)){
           return  Result.getResult(ExceptionEnum.OP_ERROR);
       }
        return  Result.getResult(ExceptionEnum.LOGIN_SUCCESS,user);
    }
    public  User getUserByToken(){
        return userMapper.getUserByToken(StrUtil.token);
    }

    public Result changePwd(User user,String oldPassword, String newPassword, String confirmPassword) {
        if(!user.getPassword().equals(oldPassword)){
            return Result.getResult(ExceptionEnum.TWO_PASSWORD_NOT_EQ);
        }
        if(oldPassword.equals(newPassword)){
            return Result.getResult(ExceptionEnum.TWO_PASSWORD_IS_SAME);
        }
        if(!newPassword.equals(confirmPassword)){
            return Result.getResult(ExceptionEnum.NEWPWD_NOT_EQ_OLDPWD);
        }
        user.setPassword(newPassword);
        Integer changpwd=userMapper.updateUser(user);
        if(!changpwd.equals(1)){
            return Result.getResult(ExceptionEnum.OP_ERROR);
        }
        return Result.getResult(ExceptionEnum.OP_SUCCESS);
    }
}
