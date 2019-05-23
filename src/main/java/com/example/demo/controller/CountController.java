package com.example.demo.controller;

import com.example.demo.entity.ExceptionEnum;
import com.example.demo.entity.Result;
import com.example.demo.service.CountService;
import com.example.demo.service.OrderService;
import com.example.demo.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CountController {
    @Autowired
    CountService countService;
    @GetMapping("/getProfitMonthToNow")
    @ApiOperation(value = "页面加载时显示本月到今天的收支情况",notes = "页面加载时显示本月到今天的收支情况")
    public Result getProfitMonthToNow(@RequestParam(value = "yearMonth",required = false)  String yearMonth){
        if(null == yearMonth){
            yearMonth = LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue();
        }

        List<String> list = StrUtil.getPeridTime(yearMonth);
        return countService.getProfitMonthToNow(list,yearMonth);
    }
}
