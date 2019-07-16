package com.example.vue_0325.demo.service.impl;

import com.example.vue_0325.demo.entity.SysUser;
import com.example.vue_0325.demo.entity.SysUserExample;
import com.example.vue_0325.demo.mapper.SysUserMapper;
import com.example.vue_0325.demo.service.SysUserService;
import com.example.vue_0325.demo.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2019-03-25 19:03
 */
@Service
public class SysServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;



    @Override
    public List<SysUser> findAll() {//调用demo中的mapper   测试连接数据库
        return sysUserMapper.selectByExample(null);
    }

    @Override
    public R login(SysUser sysUser) {//普通登录
        //方法一：select * from sys_User where username=#{username}
        SysUserExample example = new SysUserExample();//实体类
        SysUserExample.Criteria criteria = example.createCriteria();//带条件的查询
        //方法二 使用example拼条件
        criteria.andUsernameEqualTo(sysUser.getUsername());//更具用户名查询是否相同  sysUser.getUsername()获取实体类的用户名
        List<SysUser> list = sysUserMapper.selectByExample(example);//全查所有的用户
        if(list==null || list.size()==0){
            return R.error("账号不存在");
        }
        SysUser user = list.get(0);//获取第一个用户
        if(!user.getPassword().equals(sysUser.getPassword())){//判断密码是否正确
            return R.error("密码错误");
        }
        if(user.getStatus()==0){
            return R.error("账号被冻结");
        }
        return R.ok().put("user",user);
    }

    @Override
    public SysUser findByUname(String name) {//shiro登录
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(name);//条件查询拼接
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        if(sysUsers!=null && sysUsers.size()>0){
            return sysUsers.get(0);//获取第一个元素
        }
        return null;
    }

    @Override
    public ResultData findByPage(Pager pager, String search, Sorter sorter) {//用户管理列表
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysUserExample example = new SysUserExample();
        if (sorter!=null&&StringUtils.isNotEmpty(sorter.getSort())){
            example.setOrderByClause("user_id "+sorter.getOrder());
        }
        SysUserExample.Criteria criteria = example.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andUsernameLike("%"+search+"%");
        }
        List<SysUser> list = sysUserMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData data = new ResultData(info.getTotal(),info.getList());
        return data;
    }

    @Override
    public R del(List<Long> ids) {//删除
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIn(ids);//拼接sql语句
        int i = sysUserMapper.deleteByExample(example);
        if(i>0){
            return R.ok();
        }
        return R.error("删除失败");
    }

    @Override
    public R save(SysUser sysUser) {//添加
        int i = sysUserMapper.insert(sysUser);
        return i>0?R.ok():R.error("添加失败");
    }

    @Override
    public R findUser(Long userId) {//修改时回写
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        return R.ok().put("user",user);
    }

    @Override
    public R update(SysUser sysUser) {//修改
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i>0){
            return R.ok();
        }
        return R.error("修改失败");
    }

    @Override
    public SysUser findByUserPwd(String userName) {//更具用户名查询密码
        return sysUserMapper.findByUserPwd(userName);
    }

    @Override
    public int updatePwd(SysUser sysUser) {//修改密码
        return sysUserMapper.updatePwd(sysUser);
    }

    @Override
    public List<SysUser> findLockAccount() {//查询账号冻结的账号
        return sysUserMapper.findLockAccount();
    }

    @Override
    public int unLockAccount(SysUser user) {//修改状态(解封)
        return sysUserMapper.unLockAccount(user);
    }

    @Override
    public R findPieData() {//查询每个部门的人数
        List<Map<String, Object>> pieData = sysUserMapper.findPieData();//查询每个部门的人数
        List list = new ArrayList();
        for (Map<String, Object> map : pieData) {//查询出部门名称
            String name = (String) map.get("name");
            list.add(name);
        }
        return R.ok().put("pieData",pieData).put("legendData",list);
    }

    @Override
    public R findBarData() {
        List<Map<String, Object>> barData = sysUserMapper.findBarData();
        List xAxisData = new ArrayList();//x坐标
        List series0Data = new ArrayList();//男
        List series1Data = new ArrayList();//女

        for (Map<String, Object> map : barData) {
            String name = (String) map.get("NAME");
            Object boy = map.get("boy");
            Object girl = map.get("girl");
            xAxisData.add(name);
            series0Data.add(boy);
            series1Data.add(girl);
        }
        return R.ok().put("xAxisData",xAxisData).
                put("series0Data",series0Data).put("series1Data",series1Data);
    }

    @Override
    public List<Map<String, Object>> exportExcel() {//导出数据库
        return sysUserMapper.findUserForExport();
    }
}