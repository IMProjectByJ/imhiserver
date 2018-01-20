package com.mina.socket.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThisTime {
    public  static Date HaveThisTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateTime = df.format(date);
        Date date1 = null;
        try {
            date1 = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
}
