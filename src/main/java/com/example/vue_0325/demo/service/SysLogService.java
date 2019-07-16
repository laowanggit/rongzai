package com.example.vue_0325.demo.service;


import com.example.vue_0325.demo.entity.SysLog;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.Sorter;


public interface SysLogService {

     int saveLog(SysLog sysLog);

     ResultData findByPageLog(Pager pager, String search, Sorter sorter);//分页查询
}