package com.xiaoxialemi.scrabbitmqp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列
 * @author xiaoxialemi
 * @Description
 * @createTime 2021年08月25日 09:35:00
 */
@Slf4j
@Configuration
public class DelayRabbitConfig {

    // 延迟队列 TTL 名称
    private static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    // DLX，dead letter发送到的 exchange
    // 延时消息就是发送到该交换机的
    public static final String ORDER_DELAY_EXCHANGE = "order.delay.exchange";

    // routing key 名称
    // 具体消息发送在该 routingKey 的
    public static final String ORDER_DELAY_ROUTING_KEY = "order_delay";

    //立即消费的队列名称
    public static final String ORDER_QUEUE_NAME = "order.queue";

    // 立即消费的exchange
    public static final String ORDER_EXCHANGE_NAME = "order.exchange";

    //立即消费 routing key 名称
    public static final String ORDER_ROUTING_KEY = "order";


    /**
     * 延时队列
     * 向延迟队列中推送消息，时间到会转发给x-dead-letter-exchange配置的交换机
     * @return
     */
    @Bean
    public Queue delayOrderQueue(){

        Map<String,Object> map = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的exchange名称
        map.put("x-dead-letter-exchange",ORDER_EXCHANGE_NAME);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称
        map.put("x-dead-letter-routing-key",ORDER_ROUTING_KEY);
        return new Queue(ORDER_DELAY_QUEUE,true,false,false,map);

    }

    /**
     * 立即消费队列
     */
    @Bean
    public Queue orderQueue(){
        return new Queue(ORDER_QUEUE_NAME);
    }

    /**
     * 延迟交换机
     * @return
     */
    @Bean
    public DirectExchange orderDelayExchange(){
        // 一共有三种构造方法，可以只传exchange的名字，
        // 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        // 第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        // new DirectExchange(ORDER_DELAY_EXCHANGE,true,false);
        return new DirectExchange(ORDER_DELAY_EXCHANGE);
    }

    /**
     * 即时交换机
     */
    @Bean
    public TopicExchange orderTopicExchange(){
        return new TopicExchange(ORDER_EXCHANGE_NAME);
    }

    /**
     * 把延时队列和 订单延迟交换的exchange进行绑定
     * @return
     */
    @Bean
    public Binding delayExchange(){
        return BindingBuilder.bind(delayOrderQueue()).to(orderDelayExchange()).with(ORDER_DELAY_ROUTING_KEY);
    }

    /**
     * 把立即队列和 立即交换的exchange进行绑定
     * @return
     */
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderTopicExchange()).with(ORDER_ROUTING_KEY);
    }


}
