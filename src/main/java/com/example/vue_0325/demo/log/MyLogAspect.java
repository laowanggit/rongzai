package com.example.vue_0325.demo.log;


import com.alibaba.fastjson.JSON;
import com.example.vue_0325.demo.entity.SysLog;
import com.example.vue_0325.demo.service.SysLogService;
import com.example.vue_0325.demo.utils.HttpContextUtils;
import com.example.vue_0325.demo.utils.IPUtils;
import com.example.vue_0325.demo.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Aspect;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class MyLogAspect {//增强类（通知类）

    //和日志相关的类
    //注入service
    @Resource
    private SysLogService sysLogService;

    //@Pointcut(value = "execution(* com.itqf.service.impl.*.*(..))")
    //@Pointcut(value = "execution(* com.itqf.controller.*.*(..))")//描述方法的
    @Pointcut(value = "@annotation(com.example.vue_0325.demo.log.Mylog)")//描述注解的 被MyLog修饰的方法会被增强
    public void myPointcut(){}

   // @Before()//前置通知


    @AfterReturning(pointcut = "myPointcut()")
    public void after(JoinPoint joinPoint){//哪一个类能得到跟目标方法有关的信息？

        //System.out.println("后置增强！"+joinPoint.getTarget()+joinPoint.getSignature());
        //private String username;//操作人
        //System.out.println("操作人："+ShiroUtils.getCurrentUser().getUsername());
         //private String operation;//操作
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        Method method =  signature.getMethod();
        //
        Mylog mylog =  method.getAnnotation(Mylog.class);
        //System.out.println(mylog.description());

        //private String method;//调用的方法
        //System.out.println("method:"+method.getName());
         //       private String params;//参数
        //System.out.println("args :"+joinPoint.getArgs());
        //System.out.println("argsJSON :"+JSON.toJSONString(joinPoint.getArgs()));
        //得到客户端ip
        // private String ip;//调用者的ip
        //System.out.println(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        //private Date createDate; //调用时间

        String uname = ShiroUtils.getCurrentUser().getUsername();//操作人
        String opration = mylog.value();//操作方法
        //操作类名和方法名
        String methodName = joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName();
        String params = JSON.toJSONString(joinPoint.getArgs());//方法锁携带的参数
        String ip = IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());//ip地址

        SysLog sysLog  = new SysLog();//日志类
        sysLog.setCreateDate(new Date());//创建时间
        sysLog.setIp(ip);//ip
        sysLog.setMethod(methodName);
        sysLog.setParams(params);
        sysLog.setUsername(uname);
        sysLog.setOperation(opration);

        //添加日志
       int i =  sysLogService.saveLog(sysLog);//注入service

        System.out.println(i>0?"保存日志成功":"失败");
    }
}