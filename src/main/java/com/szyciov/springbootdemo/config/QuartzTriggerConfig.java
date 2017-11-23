package com.szyciov.springbootdemo.config;

import java.util.HashMap;
import java.util.Map;

import com.szyciov.springbootdemo.util.scheduler.job.TestJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.alibaba.druid.pool.DruidDataSource;

import com.szyciov.springbootdemo.service.UserService;
import com.szyciov.springbootdemo.util.scheduler.RefreshQuartz;
import com.szyciov.springbootdemo.util.scheduler.TriggerBean;
import com.szyciov.springbootdemo.util.scheduler.SimpleTriggerBean;
import com.szyciov.springbootdemo.util.scheduler.CronTriggerBean;

/**
 * 调度配置
 */
@Configuration
public class QuartzTriggerConfig {
    @Autowired
    private UserService userService;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DruidDataSource dataSource) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new ClassPathResource("public/quartz.properties"));
        factoryBean.setSchedulerContextAsMap(getSchedulerContextAsMap());
        return factoryBean;
    }

    private Map<String, Object> getSchedulerContextAsMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userService", userService);
        return map;
    }

    @Bean(initMethod = "run")
    public RefreshQuartz refreshQuartz() {
        RefreshQuartz quartz = new RefreshQuartz();
        quartz.setTriggers(getTriggers());
        return quartz;
    }

    private Map<String, TriggerBean> getTriggers() {
        Map<String, TriggerBean> map = new HashMap<String, TriggerBean>();
        //map.put("testJobSimpleTrigger", testJobSimpleTrigger());
        //map.put("testJobCronTrigger", testJobCronTrigger());
        return map;
    }

    /**
     *5秒执行一次
     * @return
     */
    @Bean
    public SimpleTriggerBean testJobSimpleTrigger() {
        SimpleTriggerBean bean = new SimpleTriggerBean();
        bean.setJobClass(TestJob.class);
        bean.setRepeatInterval(5000);
        return bean;
    }


    /**
     *定时执行
     * @return
     */
    @Bean
    public CronTriggerBean testJobCronTrigger() {
        CronTriggerBean bean = new CronTriggerBean();
        bean.setJobClass(TestJob.class);
        bean.setCronExpression("0 32 16 * * ?");
        return bean;
    }
}
