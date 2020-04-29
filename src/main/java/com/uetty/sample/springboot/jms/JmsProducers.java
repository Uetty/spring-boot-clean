package com.uetty.sample.springboot.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Queue;

@Component
public class JmsProducers {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    Queue secwikiArticleQueue;

    public void sendToTestQueue(String message, long delay) throws JMSException {
        if (delay > 0) {
            JmsSenderTool.delaySend(jmsMessagingTemplate, secwikiArticleQueue, message, delay);
        } else {
            jmsMessagingTemplate.convertAndSend(secwikiArticleQueue, message);
        }
    }

}
