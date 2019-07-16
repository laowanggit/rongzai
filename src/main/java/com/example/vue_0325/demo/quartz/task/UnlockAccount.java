package com.example.vue_0325.demo.quartz.task;

import com.example.vue_0325.demo.entity.SysUser;
import com.example.vue_0325.demo.service.SysUserService;
import com.example.vue_0325.demo.utils.Lg;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-04-01 15:33
 */
//交给spring容器来处理
@Component(value = "unLockAccount")  //beanname的名称
public class UnlockAccount {//解封账号类  ()

    @Resource
    private SysUserService sysUserService;

    public void unLock(){   //status  1正常   0冻结
        //System.out.println("解封账户");
        Lg.log("解封账号开始");
        List<SysUser> account = sysUserService.findLockAccount();//查找冻结账号
        for(SysUser user : account){
            Date date = user.getLockdate();//冻结时间
            Date now = new Date();
            long time = now.getTime()-date.getTime();//获取冻结的天数
            long day = time / (1000 * 60 * 60 * 24);//获取解封的时间(毫秒转换成天)
            if(day>=3){
                Lg.log("准备解封账号");
                SysUser sysUser = new SysUser();
                sysUser.setUserId(user.getUserId());//获取用户id
                sysUser.setStatus((byte)1);//获取用户状态
                sysUserService.unLockAccount(sysUser);//解封（修改状态）
                Lg.log("解封成功");
            }else{
                Lg.log("未到解封时间");
            }
        }

    }
}