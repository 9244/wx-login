package com.wx.wxlogin.rabbitmq.rabbitmqConfig;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description:
 * @author: WXxxx
 * @time: 2021/7/11 17:00
 */
@Configuration
public class DirectQueueConfig {

    private final String directQueueName = "directQueue";

    private final String directExchangeName = "directExchange";

    private final String routing = "directRouting";

    // 定义一个队列
    @Bean
    public Queue DirectQueue(){
        return new Queue(directQueueName,true);
    }

    // 定义一个交换机
    @Bean
    public DirectExchange DirectExchange(){
        return new DirectExchange(directExchangeName,true,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(DirectQueue()).to(DirectExchange()).with(routing);
    }


}
