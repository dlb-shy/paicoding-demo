package com.sun.controller;

import com.sun.job.TestQuartzJob;
import com.sun.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.CharSequence;

/**
 * @author sunshine
 * @date 2023/4/24 下午5:24
 */
@Controller
@ResponseBody
public class TestQuartController {
    @Autowired
    IScheduleService scheduleService;

    @RequestMapping("/test")
    public String testQuartz() throws ParseException {
        String dateString = "2023-04-25 10:45:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(dateString);
        String startCron = "0/5 * * * * ? ";
        String jobName = scheduleService.scheduleFixTimeJob(TestQuartzJob.class,
                date,
                "asdfafafa");
        System.out.println("jobName->" + jobName);
        CharSequence charSequence = "aaa";
        return jobName;

    }


}
