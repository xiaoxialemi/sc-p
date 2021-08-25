//package com.xiaoxialemi.scrabbitmqp.controller;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @author xiaoxialemi
// * @Description
// * @createTime 2021年08月04日 09:41:00
// */
//@RestController
//public class SendMessageController {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @GetMapping("sendDirectMessage")
//    public String sendDirectMessage(){
//
//        String messageId = UUID.randomUUID().toString();
//        String messageData = "hello! xiaoxialemi. It's test.";
//
//        Map<String,Object> map=new HashMap<>();
//        map.put("messageId",messageId);
//        map.put("messageData",messageData);
//        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);
//
//        return "odk";
//    }
//
//}
//
