package com.uetty.sample.springboot.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSContext;
import javax.jms.JMSSessionMode;

@Component
public class JmsConsumers {

    private static final Logger LOG = LoggerFactory.getLogger(JmsConsumers.class);

    /**
     * secwiki网站爬取
     * concurrency 1-1 表示初始化1条线程，最大可以有1条线程
     * @param message 消息
     */
    @JmsListener(destination = JmsConstant.QUEUE_TEST, concurrency = "1-1")
    @JMSSessionMode(JMSContext.CLIENT_ACKNOWLEDGE)
    public void consumeTest(String message) {
        LOG.info("consume message -> {}", message);
    }


}
