package com.mmg.rabbitmq.mq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2021/6/9
 * @Description: 收到消息后手动确认
 */
@Configuration
public class RabbitConsumerManual implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候使用的是map，所以将Map从Message当中取出来需要进行一些处理
            String msg = message.toString();
            String[] msgArray = msg.split("'");
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
            long messageId = Long.parseLong(msgMap.get("messageId"));
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            System.out.println("  MyAckReceiver  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
            System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
            //第二个参数，手动确认可以被批处理，当该参数为true时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag, true);
            //第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
//            channel.basicReject(deliveryTag, true);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false); //拒绝消息且销毁
            e.printStackTrace();
        }
    }

    //String转Map
    private Map<String, String> mapStringToMap(String str, int entryNUm) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",", entryNUm);
        Map<String, String> map = new HashMap<>();
        for (String s : strs) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
