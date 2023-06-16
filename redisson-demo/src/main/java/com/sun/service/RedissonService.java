package com.sun.service;

import java.util.Map;

/**
 * @author sunshine
 * @date 2023/5/10 上午10:47
 */
public interface RedissonService {

    Map<String, Object> getCourseInfo(Long courseId);
}
