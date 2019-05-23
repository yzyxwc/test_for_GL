package com.example.demo.util;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StrUtil {
    private final static String key = "GUOLEI_NAME";
    public static String token=null;
    public static boolean isEmpty(String msg) {
        return msg == null || msg.trim().equals("");
    }

    public static String Md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest((str+key).getBytes("utf-8")));
         return newstr;
    }
    //String格式化日期YYYY-MM-dd
    public static Date stringToDate(String str) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(str);
    }
    public static String  formateVager(String name){
        String afterName;
        if(name == null){
            afterName = "%%";
        }else{
            afterName = "%"+name+"%";
        }
        return  afterName;
    }
    /*string转BigDecimal*/
    public static BigDecimal stringToBigDecimal(String StrBd){
        BigDecimal bigDecimal=new BigDecimal(StrBd);
    //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bigDecimal=bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }
    public static Boolean verifMonth(Integer month){
       if(month != null && month >0 && month < 13){
            return true;
       }
       return  false;
    }

    public static void main(String[] args) {
        String s = null;
        List<Integer> list= JSON.parseArray(s,Integer.class);
        System.out.println(list == null);
    }

    public static List<String> getPeridTime(String yearMonth) {
        List<String> list = Lists.newArrayList();
        String start = yearMonth+"-01";
        String end = yearMonth+"-31";
        list.add(start);
        list.add(end);
        return list;

    }
}
