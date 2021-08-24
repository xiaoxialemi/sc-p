package com.xiaoxialemi.scrabbitmqp.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaoxialemi
 * @Description
 * @createTime 2021年08月04日 09:41:00
 */
@RestController
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendDirectMessage")
    public String sendDirectMessage(){

        String messageId = UUID.randomUUID().toString();
        String messageData = "hello! xiaoxialemi. It's test.";

        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);

        return "odk";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: man ";
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }

    /**
     * 消息推送到server，但是在server里找不到交换机
     * 消息推送到名为‘non-existent-exchange’的交换机上（这个交换机是没有创建没有配置的）
     * @return
     */
    @GetMapping("/testMessageAckNoExchange")
    public String testMessageAckNoExchange() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }


    /**
     * 消息推送到server，找到交换机了，但是没找到队列
     * 消息推送到名为‘noQueueExchange’的交换机上（这个交换机是没有任何队列配置的）
     * @return
     */
    @GetMapping("/testMessageAckNoQueue")
    public String testMessageAckNoQueue() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("noQueueExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/sendTopicMessageChild")
    public String sendTopicMessageWomen(@RequestParam("goodOrBad") String goodOrBad) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: child is child ";
//        Map<String, Object> womanMap = new HashMap<>();
//        womanMap.put("messageId", messageId);
//        womanMap.put("messageData", messageData);
//        womanMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        JSONObject womanMap = new JSONObject();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        womanMap.put("goodOrBad",goodOrBad);
        rabbitTemplate.convertAndSend("topicExchange", "topic.child", womanMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessageBadChild")
    public String sendTopicMessageBadChild() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: child is bad child ";
//        Map<String, Object> womanMap = new HashMap<>();
//        womanMap.put("messageId", messageId);
//        womanMap.put("messageData", messageData);
//        womanMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        JSONObject womanMap = new JSONObject();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("topicExchange", "topic.badChild", womanMap);
        return "ok";
    }

}
