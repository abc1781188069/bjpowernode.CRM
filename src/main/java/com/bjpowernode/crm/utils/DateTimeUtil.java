package com.bjpowernode.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTimeUtil {
    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }
}
