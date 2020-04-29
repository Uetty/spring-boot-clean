package com.uetty.sample.springboot.jms;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Vince
 * @Date: 2019/7/4 11:47
 */
public class JmsSenderTool {

    private static final Logger LOG = LoggerFactory.getLogger(JmsSenderTool.class);

    @SuppressWarnings("Duplicates")
    private static <T extends Serializable> ObjectMessage createObjectMessage(Session session, T t, Map<String, Object> properties, Long delay) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(t);

        if (properties != null) {
            properties.forEach((propKey, value) -> {
                try {
                    objectMessage.setObjectProperty(propKey, value);
                } catch (JMSException e) {
                    LOG.error(e.getMessage(), e);
                }
            });
        }

        if (delay != null && delay > 0) {
            objectMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        }
        return objectMessage;
    }

    public static <T extends Serializable> void sendQueueMessage(JmsTemplate jmsTemplate, Queue queue, T t, Map<String, Object> properties, long delay) {

        jmsTemplate.send(queue,
                (session) -> createObjectMessage(session, t, properties, delay));
    }

    /**
     * 延时发送
     * @param destination 发送的队列
     * @param data        发送的消息
     * @param time        延迟时间
     */
    public static <T extends Serializable> void delaySend(JmsMessagingTemplate template, Destination destination, T data, Long time) throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        // 获取连接工厂
        ConnectionFactory connectionFactory = template.getConnectionFactory();
        try {
            // 获取连接
            connection = Objects.requireNonNull(connectionFactory).createConnection();
            connection.start();
            // 获取session，true开启事务，false关闭事务
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            producer = session.createProducer(destination);
            producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
            ObjectMessage message = session.createObjectMessage(data);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            // 发送消息
            producer.send(message);
            session.commit();
        } finally {
            try {
                if (producer != null) {
                    producer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
