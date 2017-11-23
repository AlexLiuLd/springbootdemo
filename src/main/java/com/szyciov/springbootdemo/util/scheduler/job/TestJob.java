package com.szyciov.springbootdemo.util.scheduler.job;


import com.szyciov.springbootdemo.service.UserService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class TestJob extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(TestJob.class);

    private UserService service;

    public void setService(UserService service) {
        this.service = service;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext schedulerCtx = context.getScheduler().getContext();
            service = (UserService) schedulerCtx.get("userService");
            log.info("-------------------开始执行定时任务--------------------------");
            service.TestJob();
            log.info("-------------------结束执行定时任务--------------------------");

        } catch (SchedulerException e) {
            log.error("调度任务执行失败：", e);
        }

    }
}
