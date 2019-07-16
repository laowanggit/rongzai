package com.example.vue_0325.demo.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author shkstart
 * @create 2019-03-29 20:03
 */
//sql相关的类
@WebFilter(filterName = "statfilter",urlPatterns = "/*",
        initParams = {//初始化参数
                @WebInitParam(name ="exclusions" ,value = "*.js,*.css,*.jpg,*.png,*.gif,*.bmp,/druid/*")
        })  //web.xml  <filter>
public class DruidWebStatFilter extends WebStatFilter {//druid(德鲁伊)的过滤器
}
