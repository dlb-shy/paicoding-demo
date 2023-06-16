package com.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author sunshine
 * @date 2023/4/23 下午4:59
 */
@EnableCaching
@SpringBootApplication
public class CafeineApplication {
    public static void main(String[] args) {
        SpringApplication.run(CafeineApplication.class, args);
    }
}
