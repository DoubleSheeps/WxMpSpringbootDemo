package com.example.shirodemo.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringUtil {


    public static String getTotalTime(Date start,Date end){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(start) +
                " " +
                getDuration(start,end);
    }

    public static String getYear(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    public static String getMonth(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return formatter.format(date);
    }

    public static String getDay(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return formatter.format(date);
    }

    public static String getDuration(Date start, Date end){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(start) +
                "-" +
                formatter.format(end);
    }

}
