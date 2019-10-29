package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆",notes = "登录接口")
    public Result login(String name, String password){
        return userService.login(name,password);
    }
    /*@PostMapping("/getUserByToken")
    //可行的
    @ApiOperation(value = "依据token获取当前用户",notes = "token获取当前用户")
    public User getUserByToken(){
        return userService.getUserByToken();
    }*/
    @PostMapping("/changePwd")
    @ApiOperation(value = "修改用户密码",notes = "修改用户密码")
    public Result changePwd(String oldPassword, String newPassword,String confirmPassword){
        User user=userService.getUserByToken();
        return userService.changePwd(user,oldPassword,newPassword,confirmPassword);
    }
    @GetMapping("/layout")
    @ApiOperation(value = "注销登陆",notes = "注销登陆")
    public Result layout(){
        return userService.layout();
    }
}
