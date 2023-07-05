package com.example.shirodemo.config.quartz;

import com.example.shirodemo.config.SpringContextJobUtil;
import com.example.shirodemo.modules.sys.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class MyJob extends QuartzJobBean {
    private final CourseService courseService = (CourseService) SpringContextJobUtil.getBean("courseService");
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("{}触发课前提醒任务",formatter.format(now));
        courseService.beforeCourse(now);
    }
}
