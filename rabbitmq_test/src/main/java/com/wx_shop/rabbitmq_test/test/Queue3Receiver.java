package com.wx_shop.rabbitmq_test.test;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class Queue3Receiver {

    @Autowired
    RabbitTemplate myRabbitTemplete;


    @RabbitHandler
    @RabbitListener(queues  ="delay_queue2")
    public void processQueue12(Message message, Channel channel) throws Exception{
        byte[] body=message.getBody();
        System.out.println("delay_queue2 : 延迟队列2" +new String(body));
    }


//    @RabbitHandler
//    @RabbitListener(queues  ="delay_queue1")
//    public void delay_queue1(Message message, Channel channel) throws Exception{
//        byte[] body=message.getBody();
//        System.out.println("delay_queue1 : 延迟队列1" +new String(body));
//    }


}
