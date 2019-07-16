package com.example.vue_0325.demo.test;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author shkstart
 * @create 2019-03-29 11:58
 */
public class TestMd5 {
    //md5加密密码
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("jack","jack",1024);
        System.out.println(md5Hash.toString());
        md5Hash = new Md5Hash("rose","rose",1024);
        System.out.println(md5Hash.toString());
    }
}
