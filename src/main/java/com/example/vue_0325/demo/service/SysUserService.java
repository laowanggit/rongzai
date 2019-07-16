package com.example.vue_0325.demo.service;

import com.example.vue_0325.demo.entity.SysUser;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.Sorter;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2019-03-25 17:59
 */
public interface SysUserService {
     List<SysUser> findAll();

     //普通的登录
     R login(SysUser sysUser);

     //shiro登录
     SysUser findByUname(String name);

     //sorter排序，
     ResultData findByPage(Pager pager, String search, Sorter sorter);

     R del(List<Long> ids);//删除

     R save(SysUser sysUser);//添加

     R findUser(Long userId);//修改时回写

     R update(SysUser sysUser);//修改

     //R findByUserPwd(Long userId);//根据id查询用户密码

     //R updatePwd(String password ,String newPassword);//修改密码

    SysUser findByUserPwd(String userName);//根据用户名查询用户密码

    int updatePwd(SysUser sysUser);//修改密码

     List<SysUser> findLockAccount();//查询账号被封的账号

     int unLockAccount(SysUser user);//修改状态(解封)

    //查询每个部门的人数
    R findPieData();

    //查询每个部门男女员工的数量
    R findBarData();

    List<Map<String,Object>> exportExcel();//导出数据库
}