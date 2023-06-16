package com.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @author sunshine
 * @date 2023/4/24 下午3:49
 */
@SpringBootApplication
public class EnvApplication {

    public EnvApplication(Environment environment) {
        String env = environment.getProperty("biz.env");
        String whiteList = environment.getProperty("biz.whiteList");
        String ratelimit = environment.getProperty("biz.ratelimit");
        String total = environment.getProperty("biz.total");
        String profile = environment.getProperty("biz.profile");

        System.out.println("env: " + env);
        System.out.println("whiteList: " + whiteList);
        System.out.println("ratelimit: " + ratelimit);
        System.out.println("total: " + total);
        System.out.println("profile: " + profile);

    }

    public static void main(String[] args) {
        SpringApplication.run(EnvApplication.class, args);
    }
}
