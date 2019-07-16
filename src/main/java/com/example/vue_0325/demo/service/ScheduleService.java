package com.example.vue_0325.demo.service;

import com.example.vue_0325.demo.entity.ScheduleJob;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;

import java.util.List;

/**
 * @author shkstart
 * @create 2019-03-30 20:30
 */
public interface ScheduleService {
    public ResultData scheduleList(Pager pager, String search);//事务列表

    public R save(ScheduleJob scheduleJob);//添加

    public R update(ScheduleJob scheduleJob);//修改

    public R delete(List<Long> jobIds);//删除

    //暂停
    public R pause(List<Long> jobIds);
    //恢复
    public R resume(List<Long> jobIds);
    //立即执行
    public R run(List<Long> jobIds);

    //立即执行
    public R scheduleInfo(long id);
}