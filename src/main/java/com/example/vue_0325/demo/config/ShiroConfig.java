package com.example.vue_0325.demo.config;



import com.example.vue_0325.demo.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shkstart
 * @create 2019-03-27 20:30
 */
//shiro的配置文件

@Configuration   //标记是配置文件
public class ShiroConfig {
    //会话管理
    @Bean(name = "sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();//对象
        sessionManager.setSessionIdUrlRewritingEnabled(false);//禁止url拼接sessionid
        sessionManager.setGlobalSessionTimeout(1000*60*60);//session默认过期时间半个小时
        //定期清除过期的会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }
    //方法的参数相当于传入spring容器中创建的对象
    @Bean(value = "securityManager")     //核心的配置
    public SecurityManager securityManager(UserRealm userRealm,SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm);
        //缓存管理
        EhCacheManager ehCacheManager = new EhCacheManager();
        //缓存文件管理的路径
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        securityManager.setCacheManager(ehCacheManager);
        //设置cookie的缓存
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = rememberMeManager.getCookie();//cookie缓存默认值是一年
        cookie.setMaxAge(60*60*24*30);//设置为一个月
        //用来实现记住我功能
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }
    //shiro注解在spring容器中生效
    //下面三个bean注解是用来使注解能在spring中使用
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new
                DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);//aop  cglib  //使注解生效
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /*//@Bean("/logoutFilter")
    public LogoutFilter logoutFilter(){
        //shiro退出
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login.html");

        return  logoutFilter;
    }*/

    //核心配置
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        //构建对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置登录界面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置成功界面
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //没有权限的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");

//        Map<String,Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("logout",logoutFilter());
        //指定过滤器
        //shiroFilterFactoryBean.setFilters(filterMap);

        //分配权限用到的类
        //LinkedHashMap保证存储具有顺序
        LinkedHashMap<String,String> map = new LinkedHashMap<>();

        map.put("/public/**","anon");//静态js  css
        //map.put("/json/**","anon");//假数据
        map.put("/demo/**","anon");//练习页面
        map.put("/captcha.jpg","anon");//验证码
        map.put("/del","anon");//删除垃圾数据
        map.put("/sys/login","anon"); // 前端不是ajax请求
        //map.put("/logout","logout");//退出过滤器 前端不能使用ajax退出
        //map.put("/sys/menu/*","perms[\"sys:menu\"]");
        map.put("/**","user");//选中记住我能访问的资源
        map.put("/**","authc");//登录后才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}