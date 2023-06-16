package com.sun.service.impl;

import com.sun.job.HttpRemoteJob;
import com.sun.model.JobInfo;
import com.sun.util.JsonUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sun.service.JobService;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @module
 * @author: qingxu.liu
 * @date: 2022-11-15 14:48
 * @copyright
 **/
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public JobInfo save(JobInfo jobInfo) {
        //查询是否已有相同任务 jobKey可以唯一确定一个任务
        JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (Objects.nonNull(jobDetail)){
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        //任务详情
        JobDetail jobDetail = JobBuilder.newJob(HttpRemoteJob.class)
                .withDescription(JsonUtils.object2Json(jobInfo.getJsonParams()))  //任务描述
                .withIdentity(jobKey) //指定任务
                .build();
        //根据cron,TimeZone时区,指定执行计划
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(jobInfo.getCron())
                .inTimeZone(TimeZone.getTimeZone(jobInfo.getTimeZoneId()));

        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).startNow()
                .withSchedule(builder)
                .build();

        //添加任务
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
        return jobInfo;
    }

    @Override
    public JobInfo simpleSave(JobInfo jobInfo) {
        JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobInfo.getJobGroup());  //作业名称及其组名
        //判断是否有相同的作业
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail != null){
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        //定义作业的详细信息,并设置要执行的作业类名,设置作业名称及其组名
        JobDetail jobDetail = JobBuilder.newJob(HttpRemoteJob.class)
                .withDescription(JsonUtils.object2Json(jobInfo.getJsonParams()))
                .withIdentity(jobKey)
                .build()
                ;
        //简单触发器,着重与时间间隔
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                .startAt(jobInfo.getTriggerTime())
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }

        return jobInfo;
    }

    @Override
    public void remove(String jobName, String jobGroup) {
        //获取任务触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName,jobGroup));
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void resume(String jobName, String jobGroup) {
        try {
            //根据jobName,jobGroup获取jobKey 恢复任务
            scheduler.resumeJob(JobKey.jobKey(jobName,jobGroup));
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void pause(String jobName, String jobGroup) {
        try {
            //根据jobName,jobGroup获取jobKey 暂停任务
            scheduler.pauseJob(JobKey.jobKey(jobName,jobGroup));
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void trigger(String jobName, String jobGroup) {
        try {
            //根据jobName,jobGroup获取jobKey 立即执行任务
            scheduler.triggerJob(JobKey.jobKey(jobName,jobGroup));
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
    }
}