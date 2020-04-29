package com.uetty.sample.springboot.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;


@Configuration
@EnableJms
public class JmsQueuesConfigure {

    @Bean
    public Queue testQueue() {
        return new ActiveMQQueue(JmsConstant.QUEUE_TEST);
    }

}
