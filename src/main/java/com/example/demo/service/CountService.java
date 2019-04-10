package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Result;
import com.example.demo.mapper.CountMapper;
import com.example.demo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountService {
    @Autowired
    OrderMapper orderMapper;
    public Result getProfitMonthToNow() {
        List<Order> listOrder = orderMapper.getOrderByMonth();
        return null;
    }
}
