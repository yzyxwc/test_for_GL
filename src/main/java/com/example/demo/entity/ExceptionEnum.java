package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息处理枚举
 */
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    //1**用户相关,2** 数据相关 ,4**内容相关
    /**
     * 自定义错误信息
     */
    DATA_ERR(206,"异常数据"),
    DATA_HAS_EXIST(205,"重复数据"),
    NO_DATA(204,"没有数据"),
    LOSS_ERR_DATA(203,"小老弟,这波亏了"),
    LOGIN_SUCCESS(100,"登陆成功"),
    ACCOUNT_NOT_EXIST(101,"用户不存在"),
    PASSWORD_ERROR(102,"密码错误"),
    TWO_PASSWORD_NOT_EQ(103,"密码输入错误"),
    TWO_PASSWORD_IS_SAME(104,"新密码不能与旧密码一致"),
    NEWPWD_NOT_EQ_OLDPWD(105,"新密码与确认密码不一致"),
    FORMATTER_ERR_DATA(300,"格式化数据错误"),
    OP_NODATA(400,"未找到指定内容"),
    OP_SUCCESS(1000,"处理成功"),
    OP_ERROR(999,"处理失败");


    private int code;
    private String msg;
}
