package com.example.vue_0325.demo.controller;


import com.example.vue_0325.demo.entity.ScheduleJob;
import com.example.vue_0325.demo.service.ScheduleService;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SchedulerController {
    @Autowired
    private ScheduleService scheduleService;


    @RequestMapping("/schedule/job/list")
    public ResultData scheduList(Pager pager, String search){//定时任务管理列表

        return scheduleService.scheduleList(pager,search);
    }

    @RequestMapping("/schedule/job/info/{jobId}")
    public R info(@PathVariable long jobId){//修改时回写
        return scheduleService.scheduleInfo(jobId);
    }

    @RequestMapping("/schedule/job/save")
    public R save(@RequestBody ScheduleJob scheduleJob){//添加
        return scheduleService.save(scheduleJob);
    }

    @RequestMapping("/schedule/job/update")
    public R update(@RequestBody ScheduleJob scheduleJob){//修改
        return scheduleService.update(scheduleJob);
    }

    @RequestMapping("/schedule/job/del")
    public R delete(@RequestBody List<Long> jobIds){//删除
        return scheduleService.delete(jobIds);
    }

    @RequestMapping("/schedule/job/pause")
    public R pause(@RequestBody List<Long> jobIds){//暂停
        return scheduleService.pause(jobIds);
    }

    @RequestMapping("/schedule/job/resume")
    public R resume(@RequestBody List<Long> jobIds){//恢复
        return scheduleService.resume(jobIds);
    }

    @RequestMapping("/schedule/job/run")
    public R run(@RequestBody List<Long> jobIds){//立即执行
        return scheduleService.run(jobIds);
    }
}