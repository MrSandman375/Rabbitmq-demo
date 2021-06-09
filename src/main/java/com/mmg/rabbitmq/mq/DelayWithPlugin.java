package com.mmg.rabbitmq.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 延迟队列-添加插件后
 */
@Configuration
public class DelayWithPlugin {
    public static final String IMMEDIATE_QUEUE_XDELAY = "queue.xdelay.immediate";//立即消费的队列名称
    public static final String DELAYED_EXCHANGE_XDELAY = "exchange.xdelay.delayed";//延时的exchange
    public static final String DELAY_ROUTING_KEY_XDELAY = "routingkey.xdelay.delay";//延迟路由键

    // 创建一个立即消费队列
    @Bean
    public Queue immediateQueue1() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue(IMMEDIATE_QUEUE_XDELAY, true);
    }

    @Bean
    public CustomExchange delayExchange1() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_XDELAY, "x-delayed-message", true, false, map);
    }

    //把立即消费的队列和延时消费的exchange绑定在一起
    @Bean
    public Binding bindingNotify1() {
        return BindingBuilder.bind(immediateQueue1()).to(delayExchange1()).with(DELAY_ROUTING_KEY_XDELAY).noargs();
    }

}
