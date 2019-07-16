package com.example.vue_0325.demo.utils;


import com.alibaba.fastjson.JSON;
import com.example.vue_0325.demo.entity.ScheduleJob;
import com.example.vue_0325.demo.exception.RZException;
import com.example.vue_0325.demo.quartz.MyJob;
import org.quartz.*;


public class ScheduleUtils {

    //定时任务的工具类
    //定时任务增删改查，修改，暂停，恢复的工具类


    //创建定时任务
    public  static void createScheduleTask(Scheduler scheduler, ScheduleJob scheduleJob){//QuartzConfig

        try{
            //1,ScheduleFactoryBean-->Scheduler对象
            //2,JobDetail
            //在mapper.xml文件中 insert语句： useGeneratedKeys="true" keyColumn="job_id" keyProperty="jobId"
            //withIdentity 定时任务的名字
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()).build();

            //如何向定时任务传递参数
            //usingJobDate()  方法1传参数
            //方法2传参
            String s = JSON.toJSONString(scheduleJob);//转化成string类型
            jobDetail.getJobDataMap().put(SysConstant.SCHEDULE_DATA_KEY,s);


            //3,trigger启动定时任务
            //触发器
            //触发器类型  withSchedule
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(SysConstant.TRIGGER_KEY_PREFIX+scheduleJob.getJobId()).
                    withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();

            //注册任务和触发器
            scheduler.scheduleJob(jobDetail,cronTrigger);

        }catch(Exception e){
            e.printStackTrace();
            throw new RZException("创建定时任务失败");
        }
    }

    //删除定时任务
    public  static void deleteScheduleTask(Scheduler scheduler, long jobId){//QuartzConfig

        try{
            JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
            //删除定时任务
            scheduler.deleteJob(jobKey);

        }catch(Exception e){
            throw new RZException("删除定时任务失败");
        }
    }

    //修改定时任务
    public  static void updateScheduleTask(Scheduler scheduler,ScheduleJob scheduleJob){
        //修改定时任务就是修改触发器的表达式
        try{
            //trigger
            //获取触发器的键
            TriggerKey triggerKey = TriggerKey.triggerKey(SysConstant.TRIGGER_KEY_PREFIX+scheduleJob.getJobId());

            //获得原来触发器
           CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

           //修改触发器的表达式
            CronTrigger newCronTrigger =  cronTrigger.getTriggerBuilder().withSchedule
                   (CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();

           //重置触发器
            scheduler.rescheduleJob(triggerKey,newCronTrigger);
        }catch(Exception e){
            throw  new  RZException("修改任务失败，请联系管理员");
        }
    }

    public  static void pause(Scheduler scheduler,long jobId){//暂停

        //key就是他的前缀
            try{
                JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
                scheduler.pauseJob(jobKey);
            }catch(Exception e){
                throw  new  RZException("暂停任务失败，请联系管理员");
            }
    }

    public  static void resume(Scheduler scheduler,long jobId){//恢复
        try{
            JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
            scheduler.resumeJob(jobKey);
        }catch(Exception e){
            throw  new  RZException("恢复任务失败，请联系管理员");
        }
    }

    public  static void runOnce(Scheduler scheduler,long jobId){//立即运行
        try{
            JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
            scheduler.triggerJob(jobKey);
            //scheduler.start();//开始启动(代表已知执行)
        }catch(Exception e){
            throw  new  RZException("执行任务失败，请联系管理员");
        }
    }
}