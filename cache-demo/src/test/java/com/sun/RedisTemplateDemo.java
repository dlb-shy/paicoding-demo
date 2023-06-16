package com.sun;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author sunshine
 * @date 2023/5/12 下午2:53
 */
@SpringBootTest(classes = CacheApplication.class)
public class RedisTemplateDemo {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testPut() {
        redisTemplate.opsForValue().set("itwanger", "沉默王二");
        stringRedisTemplate.opsForList().rightPush("girl", "陈清扬");
        stringRedisTemplate.opsForList().rightPush("girl", "xiao");
        stringRedisTemplate.opsForList().rightPush("girl", "茶花女");
    }

    @Test
    public void testGet() {
        Object itwanger = redisTemplate.opsForValue().get("itwanger");
        System.out.println(itwanger);
        List<String> girls = stringRedisTemplate.opsForList().range("girl", 0, -1);
        System.out.println(girls);


    }
}
