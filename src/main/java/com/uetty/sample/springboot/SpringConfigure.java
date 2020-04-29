package com.uetty.sample.springboot;

import com.uetty.sample.springboot.constant.ApplicationVariable;
import com.uetty.sample.springboot.constant.Constant;
import com.uetty.sample.springboot.job.SampleJob;
import org.quartz.*;
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

    // ------------------------------------------------------ 定时器 bean配置 start

    /**
     * 配置定时任务详情类
     */
    @Bean
    public JobDetail secWikiCrawlerQuartz() {
        return JobBuilder.newJob(SampleJob.class).storeDurably().build();
    }

    /**
     * 配置定时任务执行计划
     */
    @Bean
    public Trigger secWikiCrawlerJobTrigger() {

        ScheduleBuilder<SimpleTrigger> builder = SimpleScheduleBuilder.simpleSchedule() // 开发测试时启用
                .withIntervalInSeconds(600)  //设置时间周期单位秒
                .withRepeatCount(3); // 跑3次

//        ScheduleBuilder<CronTrigger> builder = CronScheduleBuilder.cronSchedule("0 40 3,12 * * ?"); // 4点40分和12点40分

        return TriggerBuilder.newTrigger().forJob(secWikiCrawlerQuartz())
                .withIdentity("sampleJob")
                .withSchedule(builder)
                .build();
    }


    // ------------------------------------------------------ 定时器 bean 配置end
}
