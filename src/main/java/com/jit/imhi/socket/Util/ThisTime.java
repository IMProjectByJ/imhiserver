package com.jit.imhi.socket.Util;

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
    public  static Date StringToDate(String date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
       // String dateTime = df.format(date);
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
    public static String StringToMySQLDateTimeString(Date date) {
        final String[] MONTH = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        };
        StringBuffer ret = new StringBuffer();
        String dateToString = date.toString();  //like "Sat Dec 17 15:55:16 CST 2005"
        ret.append(dateToString.substring(24, 24 + 4));//append yyyy
        String sMonth = dateToString.substring(4, 4 + 3);
        for (int i = 0; i < 12; i++) {      //append mm
            if (sMonth.equalsIgnoreCase(MONTH[i])) {
                if ((i + 1) < 10)
                    ret.append("-0");
                else
                    ret.append("-");
                ret.append((i + 1));
                break;
            }
        }
        ret.append("-");
        ret.append(dateToString.substring(8, 8 + 2));
        ret.append(" ");
        ret.append(dateToString.substring(11, 11 + 8));
        return ret.toString();
    }

}
