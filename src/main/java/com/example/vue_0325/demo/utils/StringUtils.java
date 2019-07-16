package com.example.vue_0325.demo.utils;

public class StringUtils {

    public  static boolean isEmpty(String s){
        if (s==null){
            return true;
        }
        if (s.trim().length()==0){
            return true;
        }
        return false;
    }

    public  static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

    public static void main(String[]args){

        System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(" "));
        System.out.println(StringUtils.isEmpty("aa"));
    }
}