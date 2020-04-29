package com.uetty.sample.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uetty.sample.springboot.constant.ApplicationVariable;
import com.uetty.sample.springboot.constant.Constant;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfigure implements WebMvcConfigurer, ApplicationListener<ApplicationEvent>, ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationVariable.SERVER_PROFILE = applicationContext.getEnvironment().getProperty("spring.profiles.active");
        ApplicationVariable.SERVER_EDITION = applicationContext.getEnvironment().getProperty("server.edition");
        ApplicationVariable.APPLICATION_CONTEXT = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationStartedEvent) {
            ApplicationVariable.APPLICATION_STATUS = Constant.APP_STATUS_STARTED;
        } else if (applicationEvent instanceof ContextStoppedEvent) {
            ApplicationVariable.APPLICATION_STATUS = Constant.APP_STATUS_STOPPING;
        } else if (applicationEvent instanceof ContextClosedEvent) {
            ApplicationVariable.APPLICATION_STATUS = Constant.APP_STATUS_CLOSE;
        } /*else if (applicationEvent instanceof ContextRefreshedEvent) {
        }*/
    }

    // ----------------------------------------------------- redisTemplate start
    /**/
    // 默认已经配了一个StringRedisTemplate
    // 如果要使用RedisTemplate<K, V> 就得手动配置

    /**
     * 替换掉默认的JdkSerializationRedisSerializer
     * <p>可供选择的除了默认的JDKSerializer外，还有JacksonJsonRedisSerializer和GenericJackson2JsonRedisSerializer</p>
     * <p>JacksonJsonRedisSerializer和GenericJackson2JsonRedisSerializer的区别：</p>
     * <p>GenericJackson2JsonRedisSerializer在json中加入@class属性，类的全路径包名，方便反系列化。</p>
     * <p>JacksonJsonRedisSerializer如果存放了List则在反系列化的时候，如果没指定TypeReference则会报错java.util.LinkedHashMap cannot be cast</p>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    // ----------------------------------------------------- redisTemplate end
}
