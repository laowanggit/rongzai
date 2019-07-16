package com.example.vue_0325.demo.test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class TestProperties {

    public static void main(String[]args){//解决中文乱码
        try{
            Properties p = new Properties();
            InputStream is  =TestProperties.class.getClassLoader().getResourceAsStream("backdb.properties");

            InputStreamReader re = new InputStreamReader(is,"UTF-8");
            //p.load(re);
            //System.out.println(p.getProperty("jdbc.exportPath"));

            p.load(is);

            System.out.println(new String(p.getProperty("jdbc.exportPath").getBytes("iso-8859-1"),"utf-8"));
        }catch(Exception e){
            e.printStackTrace();
        }



    }

}
