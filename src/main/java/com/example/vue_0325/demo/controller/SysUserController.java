package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.entity.SysUser;
import com.example.vue_0325.demo.log.MyLogAspect;
import com.example.vue_0325.demo.log.Mylog;
import com.example.vue_0325.demo.service.SysUserService;
import com.example.vue_0325.demo.utils.*;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-03-25 19:46
 */
@RestController
public class SysUserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private Producer producer;  //验证码包下定义的类


    @RequestMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUserService.findAll();
    }

    @RequestMapping("/sys/login")
    public R login(@RequestBody SysUserDTO sysUser){//接受的是json参数   登录

        //服务端生成的验证码
        String captcha = ShiroUtils.getCaptcha();
        //客户端生成的验证码
        String c = sysUser.getCaptcha();
        if(captcha!=null&&!captcha.equalsIgnoreCase(c)){
            return R.error("验证码错误");
        }
        //普通登录
       //return sysUserService.login(sysUser);
        String s=null;
        try {
            //得到subject
            Subject subject = SecurityUtils.getSubject();
            //md5加密
            String password = sysUser.getPassword();
            Md5Hash md5Hash = new Md5Hash(password,sysUser.getUsername(),1024);
            password = md5Hash.toString();

            //把用户名和密码封装成UsernamePasswordToken对象用于shiro登录
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(),password);
            //记住我功能
            if(sysUser.isRememberMe()){
                token.setRememberMe(true);
            }
            //登录
            subject.login(token);//会调用自定义realm的认证方法
            //判断用户是否具有什么角色和权限
            System.out.println(subject.hasRole("管理员"));
            System.out.println(subject.isPermitted("sys:menu:save"));
            return R.ok();
        } catch (AuthenticationException e) {
            s=e.getMessage();//错误信息
        }
        return R.error(s);
    }


    @Mylog(value = "用户列表",description = "分页显示用户")//日志记录
    @RequiresPermissions("sys:user:list")//赋予权限
    @RequestMapping("/sys/user/list")
    public ResultData findUserByPage(Pager pager, String search, Sorter sorter){
        return sysUserService.findByPage(pager,search,sorter);
    }

    @RequestMapping("/sys/user/del")
    public R del(@RequestBody List<Long> ids){
        return sysUserService.del(ids);
    }

    @RequestMapping("/sys/user/save")
    public R add(@RequestBody SysUser sysUser){
        return sysUserService.save(sysUser);
    }

    @RequestMapping("/sys/user/info/{userId}")
    public R findUser(@PathVariable Long userId){//修改时回写
        return sysUserService.findUser(userId);
    }

    @RequestMapping("/sys/user/update")
    public R update(@RequestBody SysUser sysUser){//修改
        return sysUserService.update(sysUser);
    }

    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response){//生成验证码
        try {
            String text = producer.createText();//得到生成的验证码
            System.out.println("验证码:------->"+text);

            //把生成的验证码存到域对象中去来对比客户端中发来的验证码
            //SecurityUtils.getSubject().getSession().setAttribute("code",text);
            ShiroUtils.setAttribute("code",text);
            BufferedImage image = producer.createImage(text);//通过验证码生成一个验证码图片
            //通过流把图片返回给客户端
            ServletOutputStream outputStream = response.getOutputStream();
            //把生成的验证码展示到客户端
            ImageIO.write(image,"jpg",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //日志记录
    @Mylog(value = "查询用户信息",description = "显示用户信息")
    @RequiresPermissions("sys:user:select")//赋予权限
    @RequestMapping("/sys/user/info")
    public R userInfo(){//显示用户信息
        //从shiro中获取用户信息

        System.out.println("--->shiro中取出用户信息："+SecurityUtils.getSubject().getPrincipal());
        SysUser user = ShiroUtils.getCurrentUser();
        return R.ok().put("user",user);
    }

    @RequestMapping("sys/user/password")
    public R updatePassword(String password,String newPassword){//修改密码
        SysUser user = ShiroUtils.getCurrentUser();//从shiro中取出用户的信息
        SysUser byUserPwd = sysUserService.findByUserPwd(user.getUsername());//更具用户名查询密码
        System.out.println("密码是"+byUserPwd.getPassword());//原密码
        System.out.println("用户名是"+user.getUsername());//用户名
        Md5Hash md5Hash = new Md5Hash(password,user.getUsername(),1024);
        String pss = md5Hash.toString();
        System.out.println("--------"+pss);
        if(!pss.equals(byUserPwd.getPassword())){
            R.error("原密码输入错误");
        }else if(password==null || password.trim().length()<0){
            R.error("原密码不能为空");
        }
        md5Hash = new Md5Hash(newPassword,user.getUsername(),1024);//新密码加密
        String newpass= md5Hash.toString();
        SysUser sysUser = new SysUser();
        sysUser.setUsername(user.getUsername());
        sysUser.setPassword(newpass);
        int i = sysUserService.updatePwd(sysUser);//修改密码
        if(i>0){
            ShiroUtils.logout();//修改成功后退出登录
        }
        return i>0?R.ok():R.error("修改失败");
    }
}