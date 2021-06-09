package com.mmg.rabbitmq.mq.consumer;

import com.mmg.rabbitmq.mq.DelayWithNoPlugin;
import com.mmg.rabbitmq.mq.DelayWithPlugin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: Rabbitmq消费者  这里消费了监听队列中的消息会自动确认(ack)
 */
@Component
public class RabbitConsumer {

    /**
     * 直连型交换机消费者
     *
     * @param testMessage
     */
//    @RabbitListener(queues = "TestDirectQueue") //坚挺消息队列 TestDirectQueue，也可以是多个队列（数组形式）
//    public void process(Map<String, Object> testMessage) {
//        System.out.println("消费者收到到的消息：" + testMessage.toString());
//    }

    /**
     * topic交换机消费者
     *
     * @param testMessage
     */
    @RabbitListener(queues = "topic.man")
    public void topicManReceiver(Map<String, Object> testMessage) {
        System.out.println("topicManReceiver消费者收到的消息：" + testMessage.toString());
    }

    @RabbitListener(queues = "topic.woman")
    public void topicTotalReceiver(Map<String, Object> testMessage) {
        System.out.println("topicTotalReceiver消费者收到的消息：" + testMessage.toString());
    }

    /**
     * 扇型交换机消费者
     *
     * @param testMessage
     */
    @RabbitListener(queues = "fanout.A")
    public void fanoutAReceiver(Map<String, Object> testMessage) {
        System.out.println("fanoutAReceiver 消费者收到的消息：" + testMessage.toString());
    }

    @RabbitListener(queues = "fanout.B")
    public void fanoutBReceiver(Map<String, Object> testMessage) {
        System.out.println("fanoutBReceiver 消费者收到的消息：" + testMessage.toString());
    }

    @RabbitListener(queues = "fanout.C")
    public void fanoutCReceiver(Map<String, Object> testMessage) {
        System.out.println("fanoutCReceiver 消费者收到的消息：" + testMessage.toString());
    }

    /**
     * 无插件延迟队列消费者
     * @param testMessage
     */
    @RabbitListener(queues = DelayWithNoPlugin.IMMEDIATE_QUEUE)
    public void delayWithNoPluginMessage(Map<String, Object> testMessage){
        System.out.println("无插件延迟队列消费者收到的消息：" + testMessage.toString());
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("消息发送时间：" + testMessage.get("createTime").toString() + "\n接收到消息的时间" + nowTime);
    }

    /**
     * 插件延迟队列消费者
     * @param testMessage
     */
    @RabbitListener(queues = DelayWithPlugin.IMMEDIATE_QUEUE_XDELAY)
    public void delayWithPluginMessage(Map<String, Object> testMessage){
        System.out.println("插件延迟队列消费者收到的消息：" + testMessage.toString());
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("消息发送时间：" + testMessage.get("createTime").toString() + "\n接收到消息的时间" + nowTime);
    }

}
