package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Result;
import com.example.demo.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/insertCustomerSingle")
    @ApiOperation(value = "插入单条Customer",notes = "插入单条Customer")
    public Result insertCustomerSingle(String strDate, String name, String card, String description){
        return customerService.insertCustomerSingle(strDate,name,card,description);
    }
    @GetMapping("getCustomerList")
    @ApiOperation(value = "查询所有Customer",notes = "查询所有Customer")
    public List<Customer> getCustomerList(){
        return customerService.getCustomerList();
    }
    @GetMapping("getCustomerById")
    @ApiOperation(value = "按照id查询单条Customer",notes = "按照id查询单条Customer")
    public Customer getCustomerById(Integer id){
        return customerService.getCustomerById(id);
    }
    @GetMapping("getCustomerByNameOrIdCardOrDescripe")
    @ApiOperation(value = "NameOrIdCardOrDescripe模糊查询Customers",notes = "NameOrIdCardOrDescripe模糊查询Customers")
    public List<Customer> getCustomerByNameOrIdCardOrDescripe(String name, String idCard, String descripe){
        return customerService.getCustomerByNameOrIdCardOrDescripe(name,idCard,descripe);
    }
    @PostMapping("deleteCustomerById")
    @ApiOperation(value = "按照id删除单条Customer",notes = "按照id删除单条Customer")
    public Result  deleteCustomerById(Integer id){
        return customerService.deleteCustomerById(id);
    }
    @PostMapping("deleteCustomerByList")
    @ApiOperation(value = "按照arr删除多条Customer",notes = "按照arr删除多条Customer")
    public Result  deleteCustomerByList(String arr){
        return customerService.deleteCustomerByList(arr);
    }
    @PostMapping("updateCustomerSingle")
    @ApiOperation(value = "修改单条Customer",notes = "修改单条Customer")
    public Result updateCustomerSingle(Integer id,Integer delete,String strDate,String name,String idCard,String description){
        return customerService.updateCustomerSingle(id,delete,strDate,name,idCard,description);
    }
}
