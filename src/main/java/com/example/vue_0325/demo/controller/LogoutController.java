package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shkstart
 * @create 2019-03-29 14:29
 */
@Controller
public class LogoutController {
//    @RequestMapping("/logout")
//    public  String  logout(){
//        //清空session
//        ShiroUtils.logout();
//
//        return "redirect:/login.html";
//    }


    @RequestMapping("/logout")
    @ResponseBody
    public R logout(){
        ShiroUtils.logout();//清除缓存
        return R.ok();
    }
}