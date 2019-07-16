package com.example.vue_0325.demo.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author shkstart
 * @create 2019-03-28 16:17
 */
@Configuration    //验证码的配置文件
public class KaptchaConfig {
    @Bean
    public Producer producer(){
        DefaultKaptcha producer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER,"no");
        //验证码数量
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
        //验证码颜色
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"black");
        Config config = new Config(properties);
        producer.setConfig(config);
        return producer;
    }
}
