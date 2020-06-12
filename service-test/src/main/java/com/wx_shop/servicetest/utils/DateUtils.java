package com.wx_shop.servicetest.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

//    当前时间
    public Date NewDate(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        return date;
    }
//    格式化时间
    public String Fornat(Date date){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return format.format(date);
    }
}
