package com.example.shirodemo.config.quartz;

import com.example.shirodemo.Utils.DateStringUtil;
import com.example.shirodemo.config.SpringContextJobUtil;
import com.example.shirodemo.modules.sys.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class MyJob {
    private final CourseService courseService = (CourseService) SpringContextJobUtil.getBean("courseService");
    protected void twoHoursClassTips(){
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("{}触发课前2小时提醒任务",formatter.format(now));
        //查看下一个1小时55分钟-2小时05分钟的会上课的课程
        Date dateStart = new Date(now.getTime()+115*60*1000);
        Date dateEnd = new Date(now.getTime() + 125*60*1000);
        courseService.courseTips(dateStart,dateEnd,"VS4UF_n9VxU61nCvJYWmEuz7HZ3PLE0TNLv6fUtjkAg");
    }

    protected void oneDayClassTips(){
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("{}触发课前1天提醒任务",formatter.format(now));
        try{
            Date tomorrow = DateStringUtil.getTomorrowDate(now);
            Date afterTomorrow = DateStringUtil.getTomorrowDate(tomorrow);
            courseService.courseTips(tomorrow,afterTomorrow,"BlpfUyYyGgW2dSkvCcU-WwvnW8SYzoLysXxRFL0PUCw");
        }catch (ParseException e){
            log.error("触发课前1天提醒任务失败，日期格式转换错误。");
        }
    }
}
