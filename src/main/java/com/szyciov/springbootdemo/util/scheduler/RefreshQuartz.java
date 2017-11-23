package com.szyciov.springbootdemo.util.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;
import java.util.Set;

public class RefreshQuartz {

    @Autowired
    private Scheduler scheduler;

    private Map<String, TriggerBean> triggers;

    public void run() {

        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            if (null != jobKeys && !jobKeys.isEmpty()) {
                for (JobKey jobKey : jobKeys) {
                    TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
                    scheduler.pauseTrigger(triggerKey);
                    scheduler.unscheduleJob(triggerKey);
                    scheduler.deleteJob(jobKey);
                }
            }
            // 添加新任务
            addQuartz();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新任务
     * @throws SchedulerException
     */
    private void addQuartz() throws SchedulerException {
        for (Map.Entry<String, TriggerBean> entry : triggers.entrySet()) {
            TriggerBean triggerBean = entry.getValue();
            Class<? extends QuartzJobBean> jobClass = triggerBean.getJobClass(); //自定义的任务类，需要实现QuartzJobBean类
            String triggerName = entry.getKey(); //调度任务的名称，每个调度任务的名称保持唯一

            ScheduleBuilder<? extends Trigger> scheduleBuilder = null;
            if (triggerBean instanceof CronTriggerBean) {
                CronTriggerBean cronTriggerbean = (CronTriggerBean) triggerBean;
                String cronExpression = cronTriggerbean.getCronExpression(); // 调度任务表达式
                scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            } else if (triggerBean instanceof SimpleTriggerBean) {
                SimpleTriggerBean simpleTriggerBean = (SimpleTriggerBean) triggerBean;
                scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(simpleTriggerBean.getRepeatInterval())
                        .withRepeatCount(simpleTriggerBean.getTriggerRepeatCount());
            }
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(entry.getKey(), JobKey.DEFAULT_GROUP).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, JobKey.DEFAULT_GROUP)
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public Map<String, TriggerBean> getTriggers() {
        return triggers;
    }

    public void setTriggers(Map<String, TriggerBean> triggers) {
        this.triggers = triggers;
    }
}

