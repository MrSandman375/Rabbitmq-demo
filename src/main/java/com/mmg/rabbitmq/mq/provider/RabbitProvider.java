package com.mmg.rabbitmq.mq.provider;

import com.mmg.rabbitmq.mq.DelayWithNoPlugin;
import com.mmg.rabbitmq.mq.DelayWithPlugin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 演示用生产者
 */
@RestController
@RequestMapping("/message")
@Api(tags = "消息队列")
public class RabbitProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直连型交换机
     */
    @GetMapping("/send")
    @ApiOperation("直连型交换机发消息")
    public String sendDirectMessage() {
        Long messageId = System.currentTimeMillis();
        String messageData = "test hello rabbitmq message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting  发送到交换机：TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map, new CorrelationData(map.toString()));
        return "OK";
    }

    /**
     * topic交换机
     */
    @GetMapping("/sendTopicMessage1")
    @ApiOperation("发送主题消息1")
    public String sendTopicMessage1() {
        Long messageId = System.currentTimeMillis();
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap, new CorrelationData(manMap.toString()));
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    @ApiOperation("发送主题消息2")
    public String sendTopicMessage2() {
        Long messageId = System.currentTimeMillis();
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap, new CorrelationData(womanMap.toString()));
        return "ok";
    }

    /**
     * 扇形交换机
     */
    @GetMapping("/sendFanoutMessage")
    @ApiOperation("扇型交换机发消息")
    public String sendFanoutMessage() {
        Long messageId = System.currentTimeMillis();
        String messageData = " fanout mesage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map, new CorrelationData(map.toString()));
        return "ok";
    }

    /**
     * 无插件实现延迟消息
     */
    @GetMapping("/sendDelayMessage")
    @ApiOperation("无插件发送延迟消息")
    public String sendDelayMessage() {
        Long messageId = System.currentTimeMillis();
        String messageData = "No Plugin Delay message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(DelayWithNoPlugin.DEAD_LETTER_EXCHANGE, DelayWithNoPlugin.DELAY_ROUTING_KEY, map, message -> {
            message.getMessageProperties().setExpiration(5 * 1000 + "");
            return message;
        });
        return "ok";
    }

    /**
     * 插件实现延迟消息
     */
    @GetMapping("/sendDelayMessagePlugin")
    @ApiOperation("插件发送延迟消息")
    public String sendDelayMessagePlugin() {
        Long messageId = System.currentTimeMillis();
        String messageData = "Plugin Delay message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //带插件的延迟队列需要设置Mandatory=false，否则会报312 NO_ROUTE异常
        rabbitTemplate.setMandatory(false);
        rabbitTemplate.convertAndSend(DelayWithPlugin.DELAYED_EXCHANGE_XDELAY, DelayWithPlugin.DELAY_ROUTING_KEY_XDELAY, map, message -> {
            message.getMessageProperties().setDelay(10 * 1000);
            return message;
        });
        return "ok";
    }


}
