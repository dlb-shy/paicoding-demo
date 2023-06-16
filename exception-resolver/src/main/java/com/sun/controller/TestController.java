package com.sun.controller;

import com.sun.dto.StatusEnum;
import com.sun.exception.ForumAdviceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunshine
 * @date 2023/4/24 下午3:21
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("testControllerAdvice")
    public String testCOntrollerAdvice(){
        throw new ForumAdviceException(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "测试ControllerAdvice异常");
    }
}
