package com.mmg.rabbitmq.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 主题订阅模式
 */
@Configuration
public class TopicRabbitMQ {

    //绑定键
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitMQ.man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitMQ.woman);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    //将firstQueue和topicExchange绑定，而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man，才会发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }

}
