package com.example.vue_0325.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.ConstructorProperties;

/**
 * @author shkstart
 * @create 2019-03-29 19:54
 */
@Configuration
public class DruidConfig {//Druid(德鲁伊)的sql监控的配置文件

    @Bean(name = "druidDatasource")
    //加载配置文件中以spring.datasource开头的配置
    @ConfigurationProperties(value = "spring.datasource")
    public DataSource dataSource(){
        System.out.println("-------->init  dataSource！");
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}