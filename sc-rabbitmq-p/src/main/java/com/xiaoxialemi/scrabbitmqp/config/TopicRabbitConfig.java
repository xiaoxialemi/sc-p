package com.xiaoxialemi.scrabbitmqp.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxialemi
 * @Description
 * @createTime 2021年08月19日 17:23:00
 */
@Configuration
public class TopicRabbitConfig {
    //绑定键
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";
    public final static String child = "topic.child";
    public final static String child_bad = "topic.badChild";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.woman);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public TopicExchange noQueueExchange() {
        return new TopicExchange("noQueueExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
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


    @Bean
    public Queue thridQueue() {
        return new Queue(TopicRabbitConfig.child);
    }

    @Bean
    Binding bindingExchangeMessageChild() {
        return BindingBuilder.bind(thridQueue()).to(exchange()).with(child);
    }

//    @Bean
//    public Queue forthQueue() {
//        return new Queue(TopicRabbitConfig.child_bad);
//    }
//
//    @Bean
//    Binding bindingExchangeMessageBadChild() {
//        return BindingBuilder.bind(forthQueue()).to(exchange()).with(child_bad);
//    }

}
