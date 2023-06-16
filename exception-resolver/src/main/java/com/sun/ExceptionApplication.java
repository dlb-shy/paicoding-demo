package com.sun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.exception.ForumExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author sunshine
 * @date 2023/4/24 下午2:59
 */
@Slf4j
@SpringBootApplication
public class ExceptionApplication implements WebMvcConfigurer {
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        resolvers.add(0, new ForumExceptionHandler());
//    }

    public static void main(String[] args) {
        SpringApplication.run(ExceptionApplication.class, args);
    }
}
