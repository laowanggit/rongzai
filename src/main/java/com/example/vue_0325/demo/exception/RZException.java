package com.example.vue_0325.demo.exception;




//和shiro有关的类
public class RZException  extends  RuntimeException {

    //自定义异常处理类
    public RZException(){}

    public RZException(String msg){
        super(msg);
    }

    public RZException(String msg,Throwable throwable){
        super(msg,throwable);
    }




}
