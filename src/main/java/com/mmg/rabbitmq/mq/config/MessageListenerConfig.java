package com.mmg.rabbitmq.mq.config;

import com.mmg.rabbitmq.mq.consumer.RabbitConsumerManual;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 手动确认接收消息配置
 */
//@Configuration
//public class MessageListenerConfig {
//
//    @Autowired
//    private CachingConnectionFactory connectionFactory;
//    @Autowired
//    private RabbitConsumerManual myAckReceiver;
//
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setConcurrentConsumers(1);
//        container.setMaxConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //RabbitMQ默认是自动确认，这里改为手动确认
//        //设置一个队列
//        container.setQueueNames("TestDirectQueue");
//        //设置多个队列
//        //container.setQueueNames("TestDirectQueue1","TestDirectQueue2","TestDirectQueue3");
//
//        //另一种设置队列的方法，使用这种情况，name要设置多个，就使用addQueues
//        //container.setQueues(new Queue("TestDirectQueue1"));
//        //container.addQueues(new Queue("TestDirectQueue2"));
//        //container.addQueues(new Queue("TestDirectQueue3"));
//
//        container.setMessageListener(myAckReceiver);
//
//        return container;
//    }
//
//
//}
