package com.sun.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author sunshine
 * @date 2023/4/24 下午7:35
 */
@Data
public class JobInfo {

    private String jobName;

    private String jobGroup;

    private Map<String, Object> jsonParams;

    private String cron;

    private String timeZoneId;

    private Date triggerTime;
}
