package com.sun;

import com.sun.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sunshine
 * @date 2023/4/24 下午4:59
 */
@SpringBootApplication
public class QuartzApplication {

    public static void main(String[] args) {

        SpringApplication.run(QuartzApplication.class, args);
    }
}
