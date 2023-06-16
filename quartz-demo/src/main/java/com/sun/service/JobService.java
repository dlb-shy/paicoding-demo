package com.sun.service;


import com.sun.model.JobInfo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @module
 * @author: qingxu.liu
 * @date: 2022-11-15 14:37
 * @copyright
 **/
public interface JobService {

    /**
     * 新建一个定时任务
     * @param jobInfo 任务信息
     * @return 任务信息
     */
    JobInfo save(@RequestBody JobInfo jobInfo);

    /**
     * 新建一个简单定时任务
     * @param jobInfo 任务信息
     * @return 任务信息
     */
    JobInfo simpleSave(@RequestBody JobInfo jobInfo);

    /**
     * 删除任务
     * @param jobName 任务名称
     * @param jobGroup 任务组
     */
    void remove( String jobName,String jobGroup);

    /**
     * 恢复任务
     * @param jobName 任务名称
     * @param jobGroup 任务组
     */
    void resume(String jobName,  String jobGroup);

    /**
     * 暂停任务
     * @param jobName 任务名称
     * @param jobGroup 任务组
     */
    void pause(String jobName,  String jobGroup);

    /**
     * 立即执行任务一主要是用于执行一次任务的场景
     * @param jobName 任务名称
     * @param jobGroup 任务组
     */
    void trigger(String jobName,  String jobGroup);
}

