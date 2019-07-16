package com.example.vue_0325.demo.quartz.task;

import com.example.vue_0325.demo.utils.InitDatabaseUtils;
import com.example.vue_0325.demo.utils.Lg;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author shkstart
 * @create 2019-04-01 15:25
 */
@Component(value = "backUpDB")//交给springboot来处理
public class BackUpDBTask {//执行定时任务时备份数据库(相当于beanname的名字)

    public  void backUp(String msg){//带参构造方法备份数据库
        Lg.log("备份数据库！"+ msg);
    }


    public void backUp(){
        //System.out.println("备份数据库");
        Lg.log("备份数据库");
        try {
            Properties properties = new Properties();
            properties.setProperty("jdbc.username","root");//链接数据库的名称
            //加载配置文件
            InputStream stream = BackUpDBTask.class.getClassLoader().getResourceAsStream("backdb.properties");
            InputStreamReader isr = new InputStreamReader(stream,"utf-8");//解决中文路径乱码
            properties.load(stream);//加载流
            String command = InitDatabaseUtils.getExportCommand(properties);//获取命令
            //备份数据库的名称
            String fileName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            command= command+fileName+".sql";//后缀
            //执行doc命令
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
            //properties.getProperty打印路劲
            Lg.log("备份数据库成功",properties.getProperty("jdbc.exportPath"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}