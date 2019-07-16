package com.example.vue_0325.demo.dto;


import com.example.vue_0325.demo.entity.SysUser;


public class SysUserDTO  extends SysUser {//记住我工具类

    private String captcha;
    private boolean rememberMe;

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
