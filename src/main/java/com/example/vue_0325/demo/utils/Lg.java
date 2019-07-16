package com.example.vue_0325.demo.utils;

/**
 * @author shkstart
 * @create 2019-04-01 19:09
 */
public class Lg {//定时事务备份数据库的工具类
    public static void log(String msg){
        System.out.println("-------->"+msg);
    }
    public static void log(String msg,String v){
        System.out.println("-------->"+msg+"----->"+v);
    }
}