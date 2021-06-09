package com.mmg.rabbitmq.mq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 消息发送确认回调
 */
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        //设置连接工厂
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory，产能触发回调函数，无论消息推送的结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        //确认回调操作
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
            System.out.println("ConfirmCallback:     "+"确认情况："+ack);
            System.out.println("ConfirmCallback:     "+"原因："+cause);
        });
        //回调返回参数
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("ReturnCallback:     "+"消息："+returned.getMessage());
            System.out.println("ReturnCallback:     "+"回应码："+returned.getReplyCode());
            System.out.println("ReturnCallback:     "+"回应信息："+returned.getReplyText());
            System.out.println("ReturnCallback:     "+"交换机："+returned.getExchange());
            System.out.println("ReturnCallback:     "+"路由键："+returned.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
