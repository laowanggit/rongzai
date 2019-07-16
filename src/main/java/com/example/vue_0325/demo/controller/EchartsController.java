package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.service.SysUserService;
import com.example.vue_0325.demo.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2019-04-02 16:41
 */
@RestController
public class EchartsController {
    @Resource
    private SysUserService sysUserService;
    @RequestMapping("/sys/echarts/pie")
    public R pie(){
        return sysUserService.findPieData();
    }

    @RequestMapping("/sys/echarts/bar")
    public R bar(){
        return sysUserService.findBarData();
    }
}