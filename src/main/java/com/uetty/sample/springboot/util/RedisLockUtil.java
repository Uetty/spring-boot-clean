package com.uetty.sample.springboot.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
@Component
public class RedisLockUtil implements InitializingBean {

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    private static final String DEFAULT_LOCK_PREFIX = "lock:";
    private static final String UNLOCK_LUA =
            "local str = redis.call('get', KEYS[1]);\n" +
                    "if (str == nil) then\n" +
                    "    return 0;\n" +
                    "else\n" +
                    "    if (str == ARGV[1]) then\n" +
                    "        redis.call('del', KEYS[1]);\n" +
                    "        return 1;\n" +
                    "    end\n" +
                    "end\n" +
                    "return 0;";

    private static RedisScript<Long> UNLOCK_SCRIPT;

    private String lockPrefix = DEFAULT_LOCK_PREFIX;

    public String getLockPrefix() {
        return lockPrefix;
    }

    public void setLockPrefix(String lockPrefix) {
        this.lockPrefix = lockPrefix;
    }

    @Override
    public void afterPropertiesSet() {
        if (redisTemplate == null) {
            // 当前应用不支持redis
            return;
        }
        UNLOCK_SCRIPT = new DefaultRedisScript<>(UNLOCK_LUA, Long.class);
    }

    private void checkSupport() {
        if (UNLOCK_SCRIPT == null) {
            // 不支持redis template
            throw new RuntimeException("redis template not support");
        }
    }

    /**
     * 获取锁
     * @param key 锁名
     * @param autoReleaseSeconds 自动释放的秒数（防止宕机未释放）
     * @return 锁对象（如果未获取到锁，返回null）
     */
    public Lock lock(String key, int autoReleaseSeconds) {
        checkSupport();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        try {
            Lock lock = new Lock();
            lock.setKey(lockPrefix + key);
            lock.setToken(UUID.randomUUID().toString());

            Boolean aBoolean = opsForValue.setIfAbsent(lock.getKey(), lock.getToken(), Duration.ofSeconds(autoReleaseSeconds));
            if (Objects.equals(aBoolean, true)) {
                return lock;
            }
        } catch (Exception ignore) {
        }
        return null;
    }

    /**
     * 获取锁(2分钟后自动释放锁，防止宕机情况未释放锁)
     * @param key 锁名
     * @return 锁对象（如果未获取到锁，返回null）
     */
    public Lock lock(String key) {
        checkSupport();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        try {
            Lock lock = new Lock();
            lock.setKey(lockPrefix + key);
            lock.setToken(UUID.randomUUID().toString());

            Boolean aBoolean = opsForValue.setIfAbsent(lock.getKey(), lock.getToken(), Duration.ofMinutes(2));
            if (Objects.equals(aBoolean, true)) {
                return lock;
            }
        } catch (Exception ignore) {
        }
        return null;
    }

    @Data
    public class Lock implements AutoCloseable {

        private String key;
        private String token;

        /**
         * 释放锁
         */
        public void release() {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            // 用于校验持有锁的人是否是自己
            Object[] args = new Object[] {token};
            try {
                // 释放锁
                redisTemplate.execute(UNLOCK_SCRIPT, keys, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close() {
            release();
        }
    }
}
