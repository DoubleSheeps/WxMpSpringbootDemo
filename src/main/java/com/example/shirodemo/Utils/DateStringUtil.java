package com.example.shirodemo.Utils;

import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;

import java.text.DateFormat;
import java.text.ParseException;
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

    public static Date getDateFormString(String d,String format){
        if(format==null){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(d);
        } catch (ParseException e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"日期格式错误！");
        }
        return date;
    }

    public static Date getTomorrowDate(Date date) throws ParseException {
        Date date1 = new Date(date.getTime()+24*60*60*1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = formatter.format(date1);
        return formatter.parse(tomorrow);
    }

    public static Date getMinuteDate(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String minute = formatter.format(date);
        return formatter.parse(minute);
    }

}
