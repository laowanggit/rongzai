package com.example.vue_0325.demo.controller;


import com.example.vue_0325.demo.utils.R;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;



@RestController
public class DelController {//删除测试数据的controller

    //清除垃圾数据
    @Resource
    Scheduler scheduler;

    @RequestMapping("/del")
    public R del(String  jobId){
        try{
            JobKey jobKey = JobKey.jobKey(jobId);
            //删除定时任务
            scheduler.deleteJob(jobKey);

            return R.ok();
        }catch(Exception e){
            e.printStackTrace();
        }
        return R.error();
    }
}