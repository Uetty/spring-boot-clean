package com.uetty.sample.springboot.service;

public interface RedisTestService {
    void setString(String key, String value);

    void setObject(String key, Object value);

    String getString(String key);

    Object getObject(String key);
}
