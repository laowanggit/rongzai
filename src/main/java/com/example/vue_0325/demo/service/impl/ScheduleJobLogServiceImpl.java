package com.example.vue_0325.demo.service.impl;


import com.example.vue_0325.demo.entity.ScheduleJobLog;
import com.example.vue_0325.demo.mapper.ScheduleJobLogMapper;
import com.example.vue_0325.demo.service.ScheduleJobLogSerice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service(value = "scheduleJobLogServiceImpl")  //更具名称来获取值
public class ScheduleJobLogServiceImpl implements ScheduleJobLogSerice {//日志记录的service

    @Resource
    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    public void insertLog(ScheduleJobLog scheduleJobLog) {
        scheduleJobLogMapper.insert(scheduleJobLog);
    }
}
