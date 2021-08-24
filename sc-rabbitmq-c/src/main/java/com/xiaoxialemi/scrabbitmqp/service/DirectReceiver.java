package com.xiaoxialemi.scrabbitmqp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author xiaoxialemi
 * @Description
 * @createTime 2021年08月19日 16:58:00
 */
@Component
@Slf4j
public class DirectReceiver {

    @RabbitHandler
    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    public void process2(Map testMessage) {
        log.info("DirectReceiver2消费者收到消息  : {}", JSON.toJSONString(testMessage));
    }

    @RabbitHandler
    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    public void process1(Map testMessage) {
        log.info("DirectReceiver消费者收到消息  : {}", JSON.toJSONString(testMessage));
    }

    /**
     * 不存在队列则创建队列
     * @param testMessage
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(value = @Queue("topic.man"),exchange = @Exchange("topicExchange")))
    public void processTopicMan(Map testMessage) {
        log.info("TopicManReceiver消费者收到消息  : {}", JSON.toJSONString(testMessage));
    }

    @RabbitHandler
    @RabbitListener(queues = "topic.woman")
    public void processTopicWoman(Map testMessage) {
        log.info("TopicWoManReceiver消费者收到消息  : {}", JSON.toJSONString(testMessage));
    }

    /**
     * 手动声明队列并绑定exchange
     * 开启手动ack模式
     * ackMode = "MANUAL" 开启之后如果不手动提交，则面板一直会有Unacked
     * @param message
     * @param channel
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(value = @Queue("topic.child"),exchange = @Exchange("topicExchange")),ackMode = "MANUAL")
    public void processTopicChild(Message message, Channel channel, JSONObject body) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("topic.child消费者收到消息  body:{}",JSON.toJSONString(body));
        if("good".equals(body.getString("goodOrBad"))){
            //如果不手动确认 rabbitMQ控制台该队列一直有Unacked的消息
            channel.basicAck(deliveryTag,true);
        }else if ("bad".equals(body.getString("goodOrBad"))){
            //拒绝队列，并且重新消费
            if(deliveryTag > 10){
                //10次拒绝后丢弃
                channel.basicReject(deliveryTag,false);
            }else {
                channel.basicReject(deliveryTag,true);
            }

        }


    }

}