package com.example.vue_0325.demo.utils;


public class SysConstant {

    //自定义常量
    public  static final  String CAPTCHA_KEY="code";
    public  static  final String JOB_KEY_PREFIX="myJob_";
    public  static  final String TRIGGER_KEY_PREFIX="myTrigger_";
    public static final String SCHEDULE_DATA_KEY="schedule_data_key";//向定时任务传的参数



    //public  static final  byte NOMAL=0;
    //public  static final  byte PAUSE=1;


    public enum  ScheduleStatus{  //枚举类
        NOMAL((byte)0)   //正常
        ,PAUSE((byte)1);   //暂停

        private byte value;
        ScheduleStatus(byte value){
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }
}
