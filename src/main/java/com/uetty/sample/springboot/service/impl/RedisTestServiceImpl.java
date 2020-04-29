package com.uetty.sample.springboot.service.impl;

import com.uetty.sample.springboot.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisTestServiceImpl implements RedisTestService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> userRedisTemplate;

    @Override
    public void setString(String key, String value) {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        opsForValue.set(key, value);
    }

    @Override
    public void setObject(String key, Object value) {
        ValueOperations<String, Object> opsForValue = userRedisTemplate.opsForValue();
        opsForValue.set(key, value);
    }

    @Override
    public String getString(String key) {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        return opsForValue.get(key);
    }

    @Override
    public Object getObject(String key) {
        ValueOperations<String, Object> opsForValue = userRedisTemplate.opsForValue();
        return opsForValue.get(key);
    }
}
