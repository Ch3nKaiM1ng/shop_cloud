package com.wx_shop.servicetest.utils;

import java.text.SimpleDateFormat;

public class TimeContrastUtils {

    public boolean timeCompare(String startDate, int minute) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = "0";
        try {
            start = String.valueOf(format.parse(startDate).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis();
        String endDate = String.valueOf(time / 1000);
        int interval = Integer.parseInt(endDate) - Integer.parseInt(start);
//        if结束时间 - 开始时间 > 分钟*60
        if (interval < (minute * 60)) {
            return true;
        }
        return false;
    }


}
