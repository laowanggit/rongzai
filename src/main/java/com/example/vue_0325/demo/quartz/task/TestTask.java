package com.example.vue_0325.demo.quartz.task;

import org.springframework.stereotype.Component;

@Component(value = "testTask")
public class TestTask {//带参的定时任务

    public  void  test(){
        System.out.println("测试--无参");
    }

    public  void  test(String params){
        System.out.println("测试--带参"+params);

    }

}
