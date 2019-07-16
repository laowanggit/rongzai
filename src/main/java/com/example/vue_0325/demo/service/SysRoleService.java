package com.example.vue_0325.demo.service;

import com.example.vue_0325.demo.entity.SysRole;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.Sorter;

import java.util.List;

public interface SysRoleService {

    public List<String> findRolesByUserId(long userId);//根据用户id查询角色

    ResultData findByPageRole(Pager pager, String search, Sorter sorter);//分页查询

    R save(SysRole sysRole);//添加

    R del(List<Long> ids);//删除

    R findByRole(Long roleId);//修改时回写

    R update(SysRole sysRole);
}