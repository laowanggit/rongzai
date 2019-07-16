package com.example.vue_0325.demo.service.impl;


import com.example.vue_0325.demo.entity.SysRole;
import com.example.vue_0325.demo.entity.SysRoleExample;
import com.example.vue_0325.demo.mapper.SysRoleMapper;
import com.example.vue_0325.demo.service.SysRoleService;
import com.example.vue_0325.demo.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sysRoleServiceImpl")
public class SysRoleServiceImpl implements SysRoleService {

    //注入mapper
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findRolesByUserId(long userId) {//根据用户用户id查询用户角色
        return sysRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public ResultData findByPageRole(Pager pager, String search, Sorter sorter) {//角色列表
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysRoleExample example = new SysRoleExample();
        if (sorter!=null&& StringUtils.isNotEmpty(sorter.getSort())){
            example.setOrderByClause("role_id "+sorter.getOrder());
        }
        SysRoleExample.Criteria criteria = example.createCriteria();
        if(search!=null&&!"".equals(search)){//条件查询
            criteria.andRoleNameLike("%"+search+"%");
        }
        List<SysRole> roles = sysRoleMapper.selectByExample(example);//查询
        PageInfo info = new PageInfo(roles);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());
        return resultData;
    }

    @Override
    public R save(SysRole sysRole) {
        int i = sysRoleMapper.insert(sysRole);
        return i>0?R.ok():R.error("添加失败");
    }

    @Override
    public R del(List<Long> ids) {
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdIn(ids);
        int i = sysRoleMapper.deleteByExample(example);
        if(i>0){
            return R.ok();
        }
        return R.error("删除失败");
    }

    @Override
    public R findByRole(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        return R.ok().put("role",sysRole);
    }

    @Override
    public R update(SysRole sysRole) {
        int i = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        return i>0?R.ok():R.error("修改失败");
    }


}