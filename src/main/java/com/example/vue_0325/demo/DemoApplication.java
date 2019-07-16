package com.example.vue_0325.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//扫描servlet相关的注解，用于sql监控注解的扫描
@ServletComponentScan(basePackages = "com.example.vue_0325.demo.config")
@MapperScan(basePackages = "com.example.vue_0325.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}