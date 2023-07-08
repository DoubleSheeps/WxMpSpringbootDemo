package com.example.shirodemo.config.quartz;

import org.quartz.CronTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;
@Configuration
public class QuartzConfig {


    @Bean
    MethodInvokingJobDetailFactoryBean  jobDetail() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("myJob");
        bean.setTargetMethod("twoHoursClassTips");
        return bean;
    }

    @Bean
    MethodInvokingJobDetailFactoryBean  jobDetail2() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("myJob");
        bean.setTargetMethod("oneDayClassTips");
        return bean;
    }

    @Bean
    CronTriggerFactoryBean cronTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean ();
        bean.setJobDetail(jobDetail().getObject());
        //早上6点到晚上8点每半个小时触发一次
        bean.setCronExpression("0 0/10 6-20 * * ?");
        return bean;
    }

    @Bean
    CronTriggerFactoryBean cronTrigger2() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean ();
        bean.setJobDetail(jobDetail2().getObject());
        //晚上8点触发一次
        bean.setCronExpression("0 30 16 * * ?");
        return bean;
    }
    @Bean
    SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        CronTrigger cronTrigger = cronTrigger().getObject();
        CronTrigger cronTrigger2 = cronTrigger2().getObject();
        bean.setTriggers(cronTrigger,cronTrigger2);
        return bean;
    }
}
