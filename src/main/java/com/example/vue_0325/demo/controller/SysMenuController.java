package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.entity.SysMenu;
import com.example.vue_0325.demo.log.Mylog;
import com.example.vue_0325.demo.service.SysMenuService;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-03-26 16:18
 */
@RestController
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;
    @RequestMapping("/sys/menu/list")
    /**
     * String search  查询条件
     *String sort  排序字段
     * String order  排序方式
     */

    //日志记录
    @Mylog(value = "查询菜单信息",description = "分页查询并且按照名称查询菜单列表")
    @RequiresPermissions("sys:menu:list")  //权限
    public ResultData menuList(Integer limit, Integer offset,String search,String sort,String order){
        return sysMenuService.findByPage(limit,offset,search,sort,order);
    }

    //日志记录
    @Mylog(value = "删除菜单",description = "根据菜单编号删除菜单")
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/sys/menu/del")
    public R del(@RequestBody List<Long> ids){//批量删除
        return sysMenuService.del(ids);
    }

    //日志记录
    @Mylog(value = "查询菜单和目录",description = "查询菜单和目录")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/select")
    public R selectMenu(){//新增时查询菜单树   菜单和目录
        return sysMenuService.selectMenu();
    }

    //日志记录
    @Mylog(value = "新增菜单,目录,按钮",description = "新增菜单,目录,按钮")
    @RequiresPermissions("sys:menu:save")
    @RequestMapping("/sys/menu/save")
    public R saveMenu(@RequestBody SysMenu sysMenu){//添加
        return sysMenuService.save(sysMenu);
    }

    //日志记录
    @Mylog(value = "查询菜单",description = "查询菜单")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/info/{menuId}")
    //@PathVariable  得到地址上面占位符的值
    public R findMenu(@PathVariable Long menuId){//修改时回写
        return sysMenuService.findMenu(menuId);
    }

    //日志记录
    @Mylog(value = "修改菜单",description = "根据菜单编号修改菜单")
    @RequiresPermissions("sys:menu:update")
    @RequestMapping("/sys/menu/update")
    public R update(@RequestBody SysMenu sysMenu){//修改
        return sysMenuService.update(sysMenu);
    }

    //日志记录
    @Mylog(value = "查询用户能访问的菜单",description = "根据菜单编号查询用户能访问的菜单等信息")
    @RequestMapping("/sys/menu/user")
    public R menuUser(){//菜单栏
        return sysMenuService.findUserMenu();
    }
}