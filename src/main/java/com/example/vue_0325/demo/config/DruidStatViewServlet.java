package com.example.vue_0325.demo.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author shkstart
 * @create 2019-03-29 20:15
 */
//sql监控登录的用户名和密码设置类
@WebServlet(name = "statViewServlet",urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name="loginUsername",value ="aaa" ),
                @WebInitParam(name = "loginPassword",value = "aaa"),
                @WebInitParam(name="allow",value="10.9.21.70")  //允许谁可以访问该系统
        }) //web.xml  <servlet>r
public class DruidStatViewServlet extends StatViewServlet {//Druid的sql监控登录名和密码设置
}