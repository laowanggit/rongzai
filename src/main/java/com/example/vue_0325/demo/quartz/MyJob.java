package com.example.vue_0325.demo.quartz;

import com.alibaba.fastjson.JSON;
import com.example.vue_0325.demo.entity.ScheduleJob;
import com.example.vue_0325.demo.entity.ScheduleJobLog;
import com.example.vue_0325.demo.quartz.task.BackUpDBTask;
import com.example.vue_0325.demo.quartz.task.UnlockAccount;
import com.example.vue_0325.demo.service.ScheduleJobLogSerice;
import com.example.vue_0325.demo.utils.Lg;
import com.example.vue_0325.demo.utils.SpringContextUtils;
import com.example.vue_0325.demo.utils.StringUtils;
import com.example.vue_0325.demo.utils.SysConstant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;
import java.util.Date;


public class MyJob  implements Job {


    //定时任务相关的类
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //System.out.println("helloworld!!!!");
        //备份数据库
//        BackUpDBTask task = new BackUpDBTask();
//        task.backUp();

        //解封账号
//        UnlockAccount account = new UnlockAccount();
//        account.unLock();

        //取出jobDetail封装的类key值(向定时事务传递的参数)
        //{"beanName":"test2","cronExpression":"* * * * * ?","jobId":29,"methodName":"hahah","params":"7410","remark":"测试2"}
        //要创建不同的定时任务根据传递过来的beanName来执行
        //取出jobDetail封装的参数
        ScheduleJobLog log = new ScheduleJobLog();
        long start = System.currentTimeMillis();//获取系统当前时间
        try {
            String json = (String) context.getJobDetail().getJobDataMap().get(SysConstant.SCHEDULE_DATA_KEY);
            //System.out.println(json);
            ScheduleJob scheduleJob = JSON.parseObject(json, ScheduleJob.class);//把json转换成对象
            String beanName = scheduleJob.getBeanName();//获得beanname的名称
            String methodName = scheduleJob.getMethodName();//获得调用的方法

            String params = scheduleJob.getParams();//带参的备份数据库

            //在spring容器当中，如何根据bean的名称获得这个对象(获取spring容器的上下文)
            //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
            //ac.getBean(beanName);

            Object bean = SpringContextUtils.getBean(beanName);
            //已知方法名称，object表示的对象---->调用方法(通过反射调用)
            Class aClass = bean.getClass();
            Method method =null;
            if(StringUtils.isEmpty(params)){//如果参数为空
                method= aClass.getDeclaredMethod(methodName);//无参的
                method.invoke(bean);
            }else{
                method = aClass.getDeclaredMethod(methodName,String.class); //带参的
                method.invoke(bean,params);
            }
            log.setBeanName(beanName);
            log.setMethodName(methodName);
            log.setJobId(scheduleJob.getJobId());
            log.setCreateTime(new Date());
            log.setParams(params);//指定参数

            log.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());//状态

        } catch (Exception e) {
            e.printStackTrace();
            log.setError(e.getMessage());
        }

        //定时事务日志记录
        long end = System.currentTimeMillis();
        //通过spring容器并根据bean名称拿到service日志
        ScheduleJobLogSerice scheduleJobLogSerice =
                (ScheduleJobLogSerice) SpringContextUtils.getBean("scheduleJobLogServiceImpl");
        log.setTimes(end-start);//定时任务执行的时间
        scheduleJobLogSerice.insertLog(log);
        Lg.log("定时任务日志记录成功！");
    }
}