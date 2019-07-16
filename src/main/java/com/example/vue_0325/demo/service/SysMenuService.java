package com.example.vue_0325.demo.service;

import com.example.vue_0325.demo.entity.SysMenu;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;

import java.util.List;

/**
 * @author shkstart
 * @create 2019-03-26 16:31
 */
public interface SysMenuService {
    ResultData findByPage(Integer limit,Integer offset,String search,String sort,String order);//分页条件查询

    R del(List<Long> ids);//删除

    R selectMenu();//查询菜单和目录

    R save(SysMenu sysMenu);//添加

    R findMenu(Long menuId);//修改时回写

    R update(SysMenu sysMenu);//修改

    List<String> findPermsByUserId(long userId);//根据用户id查询权限

    R findUserMenu();//查询菜单和目录
}