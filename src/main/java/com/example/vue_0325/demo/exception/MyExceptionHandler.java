package com.example.vue_0325.demo.exception;

import com.example.vue_0325.demo.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author shkstart
 * @create 2019-03-29 19:36
 */
//@ControllerAdvice  //异常处理的注解可以跳转页面
@RestControllerAdvice   //响应json的
public class MyExceptionHandler {//全局异常处理类(和shiro有关的类)

    @ExceptionHandler(AuthorizationException.class)
    public R handlerException(AuthorizationException e){//没有权限的异常类
        return R.error("您没有权限操作，请联系管理员");
    }

    @ExceptionHandler(RZException.class)
    public R handlerException(RZException e){//自定义异常
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e){//捕获全局异常类
        return R.error(e.getMessage());
    }
}