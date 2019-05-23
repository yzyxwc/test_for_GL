package com.example.demo.service;

import com.example.demo.entity.Miscellaneous;
import com.example.demo.entity.Order;
import com.example.demo.entity.Result;
import com.example.demo.entity.ThisMonthProfit;
import com.example.demo.mapper.CountMapper;
import com.example.demo.mapper.MiscellaneousMapper;
import com.example.demo.mapper.OrderMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CountService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MiscellaneousMapper miscellaneousMapper;
    public Result getProfitMonthToNow(List<String> list, String initMonth) {
        List<Order> listOrder = orderMapper.getOrderByMonth(list.get(0),list.get(1));
        //本月订单 获取总利润
        Double totalOrder = listOrder.stream().mapToDouble(item->{
            return item.getSingleprofit().doubleValue()*item.getOrderpeoplecount();
        }).sum();

         //统计闲散支出
        List<Miscellaneous> allMiscellaneousByMonth = miscellaneousMapper.getAllMiscellaneousByMonth(list.get(0),list.get(1));
        Double cost = allMiscellaneousByMonth.stream().mapToDouble(item->{
            return item.getCost().doubleValue();
        }).sum();
        //封装
        ThisMonthProfit thisMonthProfit = new ThisMonthProfit(initMonth,listOrder.size(),cost,allMiscellaneousByMonth.size(),totalOrder,totalOrder-cost);
        return Result.getResult(thisMonthProfit);
    }

    public static void main(String[] args) {
        //测试jdk8Stream
        List<Order> listOrder = Lists.newArrayList();
        Order order1 = new Order();
        order1.setOrderpeoplecount(2);
        order1.setSingleprofit(new BigDecimal(123.5));
        listOrder.add(order1);
        Order order2 = new Order();
        order2.setOrderpeoplecount(5);
        order2.setSingleprofit(new BigDecimal(20));
        listOrder.add(order2);
        Order order3 = new Order();
        order3.setOrderpeoplecount(24);
        order3.setSingleprofit(new BigDecimal(50.7));
        listOrder.add(order3);
        //本月订单 获取总利润
        Double totalOrder = listOrder.stream().map(item->{
            return item.getSingleprofit().doubleValue()*item.getOrderpeoplecount();
        }).mapToDouble(Double::doubleValue).sum();
        System.out.println(totalOrder);
    }
}
