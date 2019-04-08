package com.example.demo.controller;

import com.example.demo.entity.Organizer;
import com.example.demo.entity.Result;
import com.example.demo.service.OrganizerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class OrganizerController {
    @Autowired
    OrganizerService organizerService;

    @PostMapping("/addOrganizer")
    @ApiOperation(value = "新增组织者",notes = "新增组织者")
    public Result addOrganizer(String name, String organizerdescripe){
        return organizerService.insertOrganizerSingle(name,organizerdescripe);
    }
    /*考虑做一下表格上传新增*/
    @GetMapping("/selectOrganizerById")
    @ApiOperation(value = "id查询组织者",notes = "id查询组织者")
    public Organizer selectOrganizerById(Integer id){
        return organizerService.selectOrganizerById(id);
    }
    @PostMapping("/selectVagueOrganizerByNameAndDescripe")
    @ApiOperation(value = "模糊查询组织者",notes = "模糊查询组织者")
    public List<Organizer> selectVagueOrganizerByNameAndDescripe(String name, String organizerdescripe){
        return organizerService.selectVagueOrganizerByNameAndDescripe(name,organizerdescripe);
    }
    @PostMapping("/selectAllOrganizer")
    @ApiOperation(value = "查询所有组织者",notes = "查询所有组织者")
    public List<Organizer> selectAllOrganizer(){
        return organizerService.selectAllOrganizer();
    }
    @PostMapping("/updateOrganizerById")
    @ApiOperation(value = "id修改组织者",notes = "id修改组织者")
    public Result updateOrganizerById(Integer id,Integer delete,String name, String organizerdescripe){
        return organizerService.updateOrganizerById(id,delete,name,organizerdescripe);
    }
    @PostMapping("/deleteOrgernizerById")
    @ApiOperation(value = "id删除组织者",notes = "id删除组织者")
    public Result deleteOrgernizerById(Integer id){
        return organizerService.deleteOrgernizerById(id);
    }
    @PostMapping("/deleteOrgernizerByList")
    @ApiOperation(value = "list删除组织者",notes = "list删除组织者")
    public Result deleteOrgernizerByList(String arr){
        return organizerService.deleteOrgernizerByList(arr);
    }
}
