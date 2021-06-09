package com.mmg.rabbitmq.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 直连型交换机
 * 多个消费者消费同一个队列会默认轮询，不会重复消费
 */
@Configuration
public class DirectRabbitMQ {
    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    // return new Queue("TestDirectQueue",true,true,false);
    // 一般设置一下队列的持久化就好,其余两个就是默认false
    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue", true);
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    public DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange", true, false);
    }

    //绑定 将队列和交换机绑定，并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }

    // 用于演示消息推送异常（只有交换机未绑定队列）
    @Bean
    public DirectExchange longlyDirectExchange() {
        return new DirectExchange("longlyDirectExchange");
    }

}
