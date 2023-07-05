package com.example.shirodemo.config.quartz;

import org.quartz.CronTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;
@Configuration
public class QuartzConfig {


    @Bean
    JobDetailFactoryBean jobDetail2() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(MyJob.class);
        bean.setDurability(true);
        return bean;
    }
    @Bean
    CronTriggerFactoryBean cronTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean ();
        bean.setJobDetail(jobDetail2().getObject());
        //早上6点到晚上8点每半个小时触发一次
        bean.setCronExpression("0 0/30 6-20 * * ?");
        return bean;
    }
    @Bean
    SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        CronTrigger cronTrigger = cronTrigger().getObject();
        bean.setTriggers(cronTrigger);
        return bean;
    }
}
