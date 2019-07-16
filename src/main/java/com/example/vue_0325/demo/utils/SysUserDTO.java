package com.example.vue_0325.demo.utils;


import com.example.vue_0325.demo.entity.SysUser;


public class SysUserDTO extends SysUser {
    //user类中扩展字段的工具类
    private String captcha;  //验证码
    private boolean rememberMe;  //记住我

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
