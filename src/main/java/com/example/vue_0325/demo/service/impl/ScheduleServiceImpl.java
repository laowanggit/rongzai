package com.example.vue_0325.demo.service.impl;

import com.example.vue_0325.demo.entity.ScheduleJob;
import com.example.vue_0325.demo.entity.ScheduleJobExample;
import com.example.vue_0325.demo.mapper.ScheduleJobMapper;
import com.example.vue_0325.demo.service.ScheduleService;
import com.example.vue_0325.demo.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.quartz.Scheduler;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class ScheduleServiceImpl  implements ScheduleService {

    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    //依赖Scheduler    在QuartzConfig定义
    @Resource
    private Scheduler scheduler;


    @Override
    public ResultData scheduleList(Pager pager, String search) {//定时事务管理列表

        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());

        //在有条件查询的时候用这个方法
        ScheduleJobExample example = new ScheduleJobExample();
        if (StringUtils.isNotEmpty(search)){//动态拼接条件
            ScheduleJobExample.Criteria criteria = example.createCriteria();//指定条件
            criteria.andBeanNameLike("%"+search+"%");
        }
        List<ScheduleJob> list = scheduleJobMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo(list);

        //ResultData  分页工具类
        ResultData resultData = new ResultData(pageInfo.getTotal(),pageInfo.getList());

        return resultData;
    }

    @Override
    public R save(ScheduleJob scheduleJob) {//添加
        //新增是设置状态(设置为正常)
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
        //创建时间
        scheduleJob.setCreateTime(new Date());
        //1,保存Schedule_job表
        int i = scheduleJobMapper.insert(scheduleJob);

        //2,真正定时任务的创建   关联定时任务
        ScheduleUtils.createScheduleTask(scheduler,scheduleJob);

        return i>0?R.ok():R.error();
    }



    @Override
    public R delete(List<Long> jobIds) {//删除
        //1,删除Schedule_job表的记录
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria =  example.createCriteria();
        criteria.andJobIdIn(jobIds);
        int i = scheduleJobMapper.deleteByExample(example);

        //2,删除真正的定时任务(删除库的记录)
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleTask(scheduler,jobId);
        }

        return i>0?R.ok():R.error();
    }

    @Override
    public R update(ScheduleJob scheduleJob) {//修改
        //1,修改数据库Schedule_job表
        int i = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
        //2,修改真正的定时任务
        ScheduleUtils.updateScheduleTask(scheduler,scheduleJob);
        return i>0?R.ok():R.error();
    }

    @Override
    public R pause(List<Long> jobIds) {//暂停
        //1，修改数据库Schedule_job表的状态 status
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria =  example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());//暂停状态
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);

        //2,真正暂停任务
        for (Long jobId : jobIds) {
            ScheduleUtils.pause(scheduler,jobId);
        }

        return i>0?R.ok():R.error();
    }

    @Override
    public R resume(List<Long> jobIds) {//恢复
        //1,修改表的状态
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria =  example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());//恢复状态
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);

        //2,真正恢复任务
        for (Long jobId : jobIds) {
            ScheduleUtils.resume(scheduler,jobId);
        }
        return i>0?R.ok():R.error();
    }

    @Override
    public R run(List<Long> jobIds) {//立即执行
//        ScheduleJobExample example = new ScheduleJobExample();
//        ScheduleJobExample.Criteria criteria =  example.createCriteria();
//        criteria.andJobIdIn(jobIds);
//        ScheduleJob scheduleJob = new ScheduleJob();
//        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());
//        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);

        for (Long jobId : jobIds) {
            ScheduleUtils.runOnce(scheduler,jobId);
        }
        return R.ok();
    }

    @Override
    public R scheduleInfo(long id) {//修改时回写
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
        return R.ok().put("scheduleJob",scheduleJob);
    }
}