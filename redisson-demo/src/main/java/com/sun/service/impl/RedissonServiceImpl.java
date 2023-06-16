package com.sun.service.impl;

import com.sun.service.RedissonService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author sunshine
 * @date 2023/5/10 上午10:47
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Map<String, Object> getCourseInfo(Long courseId) {
        RLock lock = redissonClient.getLock("coursequerylock:" + courseId);
        lock.lock();
        try {

        }finally {
            // 手动释放锁
            lock.unlock();
        }

        return null;
    }
}
