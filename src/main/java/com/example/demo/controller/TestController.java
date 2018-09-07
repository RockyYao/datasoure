package com.example.demo.controller;

import com.example.demo.TargetDataSource;
import com.example.demo.datasoure1.mapper.TestInfoMapper;
import com.example.demo.datasoure1.pojo.TestInfo;
import com.example.demo.enumDemo.DataSoureKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Autowired
    private TestInfoMapper mapper;


    @TargetDataSource(dataSourceKey = DataSoureKey.datasource)
    @RequestMapping("/{id}")
    public TestInfo findOne(@PathVariable Integer id){
         return     mapper.findOne(id);
    }


    @TargetDataSource(dataSourceKey = DataSoureKey.datasource2)
    @RequestMapping("/2/{id}")
    public TestInfo findOne2(@PathVariable Integer id){
        return     mapper.findOne(id);
    }

    @TargetDataSource(dataSourceKey = DataSoureKey.datasource3)
    @RequestMapping("/3/{id}")
    public TestInfo findOne3(@PathVariable Integer id){
        return     mapper.findOne(id);
    }


}
