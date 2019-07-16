package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.entity.SysRole;
import com.example.vue_0325.demo.log.Mylog;
import com.example.vue_0325.demo.service.SysRoleService;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.Sorter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-03-30 14:24
 */
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    //日志记录
    @Mylog(value = "角色列表",description = "分页显示角色")//日志记录
    @RequiresPermissions("sys:role:list")
    @RequestMapping("/sys/role/list")
    public ResultData roleList(Pager pager, String search, Sorter sorter){//角色列表
        return sysRoleService.findByPageRole(pager,search,sorter);
    }

    @RequestMapping("/sys/role/save")
    public R save(@RequestBody SysRole sysRole){//添加
        return sysRoleService.save(sysRole);
    }

    @RequestMapping("/sys/role/del")
    public R delete(@RequestBody List<Long> ids){//删除
        return sysRoleService.del(ids);
    }


    @RequestMapping("/sys/role/info/{roleId}")
    public R findRole(@PathVariable Long roleId){//修改时回写
        return sysRoleService.findByRole(roleId);
    }

    @RequestMapping("/sys/role/update")
    public R update(@RequestBody SysRole sysRole){//修改
        return sysRoleService.update(sysRole);
    }
}