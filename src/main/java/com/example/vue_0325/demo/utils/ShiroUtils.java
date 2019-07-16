package com.example.vue_0325.demo.utils;

import com.example.vue_0325.demo.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

/**
 * @author shkstart
 * @create 2019-03-28 16:57
 */
public class ShiroUtils {//验证码和session的工具类

    //private static int number = 1024;//解密次数

    //SecurityUtils.getSubject().getSession().setAttribute("code",text);
    public static Session getSession(){//获取session
        return SecurityUtils.getSubject().getSession();
    }
    public static void setAttribute(String k,String v){//存值
        // k代表键  v代表的值
        getSession().setAttribute(k,v);
    }
    public static Object getAttribute(String k){//获取值
       return getSession().getAttribute(k);
    }

    public static String getCaptcha(){//获取验证码
        return getAttribute("code")+"";
    }
    public static SysUser getCurrentUser(){//获取登录时用户名
        return  (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static void logout(){//用于退出登录
        SecurityUtils.getSubject().logout();
    }


    public static long getUserId(){//获取登录时用户的id
        return getCurrentUser().getUserId();
    }

  /*  *//**
     * shiro Base64解密
     * *//*
    public static String decBase64(String souce){
        return Base64.decodeToString(souce);
    }

    public static String decode(String souce){//解密
        for(int i = 0 ; i <= number ;i++){
            souce = decBase64(souce);
        }
        return souce;
    }*/

}