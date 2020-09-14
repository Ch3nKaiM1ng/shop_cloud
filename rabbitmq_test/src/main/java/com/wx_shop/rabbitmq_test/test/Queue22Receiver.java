package com.wx_shop.rabbitmq_test.test;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue("myQueue2"))
public class Queue22Receiver {

    @Autowired
    RabbitTemplate myRabbitTemplete;


    @RabbitHandler
    public void processQueue22(Integer num, Message message, Channel channel) throws Exception{
        channel.basicQos(0,5,true);
        System.out.println("消费者2号 : " + num);
        Thread.sleep(1000);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
