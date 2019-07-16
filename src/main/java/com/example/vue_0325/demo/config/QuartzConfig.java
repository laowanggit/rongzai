package com.example.vue_0325.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {  //定时事务的配置文件(quartz)

    //第一步

    /**
     * org.quartz.scheduler.instanceName=myQuartzScheduler
     * org.quartz.scheduler.instanceid=AUTO
     * # Configure ThreadPool 线程池属性
     * #线程池的实现类（一般使用SimpleThreadPool即可满足几乎所有用户的需求）
     * org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
     * #指定线程数，至少为1（无默认值）(一般设置为1-100直接的整数合适)
     * org.quartz.threadPool.threadCount=10
     * #设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5）
     * org.quartz.threadPool.threadPriority=5
     * #保存job和Trigger的状态信息到内存中的类
     * org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
     * org.quartz.jobStore.tablePrefix=QRTZ_
     * @return
     */


    //把定时任务保存到数据库中

    //druidDatasource   druid(德鲁伊)的连接池
    //Qualifier用到方法上，代表根据名称注入某个对象
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(value = "druidDatasource") DataSource dataSource){

        //定时任务的对象
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties p = new Properties();//数据持久化

        //后面引号中是保存数据库中的名字
        p.setProperty("org.quartz.scheduler.instanceName","MyQuartzScheduler");
        p.setProperty("org.quartz.scheduler.instanceid","AUTO");//线程池属性
        //线程池相关的类
        p.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
        p.setProperty("org.quartz.threadPool.threadCount","10");//线程池数量
        //用来保存到数据库中（默认保存到内存中）
        p.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
        //线程池的前缀
        p.setProperty(" org.quartz.jobStore.tablePrefix","QRTZ_");

        schedulerFactoryBean.setQuartzProperties(p);

        //启动quartz监控
        schedulerFactoryBean.setAutoStartup(true);//自动启动

        //是否允许修改
        schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖已知job

        schedulerFactoryBean.setStartupDelay(5);//延迟时间5秒

        schedulerFactoryBean.setDataSource(dataSource);//设置dataSource

        return  schedulerFactoryBean;

    }

}
