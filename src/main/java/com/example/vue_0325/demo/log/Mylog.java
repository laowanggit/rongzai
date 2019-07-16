package com.example.vue_0325.demo.log;

import java.lang.annotation.*;

/**
 * @author shkstart
 * @create 2019-03-29 21:59
 */


//使用4个元注解(重点前两个注解)
    @Target(ElementType.METHOD)//描述注解使用的位置
    @Retention(RetentionPolicy.RUNTIME) //能否被反射读取到
    @Documented
    @Inherited
public @interface Mylog {//日志变量类
    //两个成员变量
    String value();//记录方法的功能
    String description();//详细描述的方法
}