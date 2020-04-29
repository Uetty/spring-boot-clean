package com.uetty.sample.springboot;

import com.uetty.sample.springboot.constant.ApplicationVariable;
import com.uetty.sample.springboot.constant.Constant;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
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
}
