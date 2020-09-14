package com.wx_shop.rabbitmq_test.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface SendMessageService extends RabbitTemplate.ConfirmCallback {

    void sendMessage(String exchange,String routekey,Object message);
}
