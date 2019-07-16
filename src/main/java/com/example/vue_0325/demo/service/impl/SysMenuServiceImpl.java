package com.example.vue_0325.demo.service.impl;

import com.example.vue_0325.demo.entity.SysMenu;
import com.example.vue_0325.demo.entity.SysMenuExample;
import com.example.vue_0325.demo.mapper.SysMenuMapper;
import com.example.vue_0325.demo.service.SysMenuService;
import com.example.vue_0325.demo.utils.R;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.ShiroUtils;
import com.example.vue_0325.demo.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shkstart
 * @create 2019-03-26 16:32
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Override
    public ResultData findByPage(Integer limit, Integer offset,String search,String sort,String order) {
        /**
         *  @param offset  起始记录
         *  @param limit 每页显示数量
         */
        PageHelper.offsetPage(offset,limit);//启动分页
        SysMenuExample  example = new SysMenuExample();//实体类
        SysMenuExample.Criteria criteria = example.createCriteria();//获取条件
        if(search!=null && !"".equals(search)){
            criteria.andNameLike("%"+search+"%");//模糊查询
        }
        if(sort!=null && sort.equals("menuId")){//接受前台传来的id
            sort="menu_id";//转换成数据库的字段
        }
        example.setOrderByClause(sort+" "+order);//排序字段
        List<SysMenu> menus = sysMenuMapper.selectByExample(example);
        PageInfo info = new PageInfo(menus);
        long total = info.getTotal();
        List list = info.getList();
        ResultData resultData = new ResultData(total,list);
        return resultData;
    }

    @Override
    public R del(List<Long> ids) {//批量删除
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();//删除的条件
        for(Long id:ids){
            if(id<31){//限定删除的的判断
                return R.error("系统菜单，不能删除！请核对");
            }
        }
        //where id menu_in (?,?);
        criteria.andMenuIdIn(ids);//拼接sql语句
        int i = sysMenuMapper.deleteByExample(example);//删除
        if(i>0){
            return R.ok();
        }
        return R.error("删除失败");
    }

    /**
     * SELECT * FROM sys_menu
     * WHERE TYPE!=2
     * @return
     */
    @Override
    public R selectMenu() {//添加时查询目录菜单
       //自己拼写sql语句
        List<SysMenu> menus = sysMenuMapper.findMenuNotButton();

        //数据库：系统菜单：menu_id 1  parent_id 0
        //      一级目录： menu_id 0  parent_id -1
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("一级目录");
        sysMenu.setOrderNum(0);
        menus.add(sysMenu);
        return R.ok().put("menuList",menus);
    }

    @Override
    public R save(SysMenu sysMenu) {//添加
        int i = sysMenuMapper.insert(sysMenu);
        return i>0?R.ok():R.error("新增失败");
    }

    @Override
    public R findMenu(Long menuId) {//修改时回写
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);//得到主键
        return R.ok().put("menu",sysMenu);//menu  前台页面传一个menu的参数
    }

    @Override
    public R update(SysMenu sysMenu) {//修改
        int i = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        if(i>0){
            return R.ok();
        }
        return R.error("修改失败");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {//根据用户id查询用户权限
        //null  或者 "sys:user:list,sys:user:info,sys:user:select"
        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set set = new HashSet<String>();//去重
        for (String s : list) {
            if (StringUtils.isNotEmpty(s)){
                //"sys:user:list,sys:user:info,sys:user:select"
                //"sys:user:list"
                String ss[] =  s.split(",");//拆分权限
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }

        List<String> newList = new ArrayList<>();
        newList.addAll(set);

        return newList;
    }

    @Override
    public R findUserMenu() {//查询目录和菜单
        long userId = ShiroUtils.getUserId();//获取登录用户的id
        List<SysMenu> dir = sysMenuMapper.findDir(userId);//根据用户id查询目录
        for (SysMenu menu : dir) {
            //根据用户id和目录id查询菜单
            List<SysMenu> menus = sysMenuMapper.findMenu(menu.getMenuId(), userId);
            menu.setList(menus);
        }
        List<String> byUserId = this.findPermsByUserId(userId);//根据用户id查询权限
        return R.ok().put("menuList",dir).put("permissions",byUserId);//封装成前台传过来的参数
    }
}