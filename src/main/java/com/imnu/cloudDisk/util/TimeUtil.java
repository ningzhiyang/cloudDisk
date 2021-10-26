package com.imnu.cloudDisk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
*@Descritpion:时间静态类
**/
public class TimeUtil {
    public static String getCurrentTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentdate=sdf.format(new Date());
        return currentdate;
    }
    public static  String time(){

        return System.currentTimeMillis()+"";
    }
}
