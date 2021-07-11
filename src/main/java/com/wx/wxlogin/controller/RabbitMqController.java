package com.wx.wxlogin.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/7/11 17:25
 */
@RestController
@RequestMapping(value = "/rabbitmq")
public class RabbitMqController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/sendMesage/{msg}")
    public void sendMesage(@PathVariable(value = "msg") String msg){
        rabbitTemplate.convertAndSend("directRouting",msg);
    }
}
