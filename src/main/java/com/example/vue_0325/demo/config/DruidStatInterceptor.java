package com.example.vue_0325.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
//监控applicationContext-monitor.xml配置文件中与sql监控相关的元素aop
@ImportResource(locations = "classpath:applicationContext-monitor.xml")
//classpath:applicationContext-monitor.xml
public class DruidStatInterceptor {//spring监控类
}