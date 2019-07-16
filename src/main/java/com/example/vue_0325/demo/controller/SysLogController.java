package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.service.SysLogService;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.Sorter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2019-04-02 12:06
 */
@RestController
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    @RequestMapping("/sys/log/list")
    public ResultData logList(Pager pager, String search, Sorter sorter){
        return sysLogService.findByPageLog(pager,search,sorter);
    }
}
