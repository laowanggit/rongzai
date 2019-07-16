package com.example.vue_0325.demo.realm;

import com.example.vue_0325.demo.entity.SysUser;
import com.example.vue_0325.demo.service.SysMenuService;
import com.example.vue_0325.demo.service.SysRoleService;
import com.example.vue_0325.demo.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//shiro授权和认证
@Component(value = "userRealm")  // 衍生出三个注解： @Repository @Service  @Controller
public class UserRealm extends AuthorizingRealm {//核心类

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("----->授权");
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        List<String> rolesByUserId = sysRoleService.findRolesByUserId(user.getUserId());//获取用户角色
        List<String> permsByUserId = sysMenuService.findPermsByUserId(user.getUserId());//获取用户权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();//用来授权
        info.addRoles(rolesByUserId);//添加角色
        info.addStringPermissions(permsByUserId);//添加权限
        System.out.println("----->授权over!");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("----->认证");
        UsernamePasswordToken u = (UsernamePasswordToken) token;//获取token对象
        String username = u.getUsername();//获取用户名
        String password = new String(u.getPassword());//获取密码
        //调用service层的方法
        SysUser uname = sysUserService.findByUname(username);//根据用户名查询
        if(uname==null){//判断账号是否为空或者错误
            throw new UnknownAccountException("账号未知！");
        }
        if(!uname.getPassword().equals(password)){
            throw new IncorrectCredentialsException("密码错误！");
        }
        if(uname.getStatus()==0){
            throw new LockedAccountException("账号被冻结！");
        }
        //登录
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(uname,password,this.getName());
        return info;
    }
}