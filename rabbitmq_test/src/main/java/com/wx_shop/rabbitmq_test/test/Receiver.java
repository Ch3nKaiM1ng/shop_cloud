package com.wx_shop.rabbitmq_test.test;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rabbitmq.client.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Receiver {

    @Autowired
    RabbitTemplate myRabbitTemplete;


    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("myQueue1"))
    public void processQueue1(Integer num, Message message, Channel channel) throws Exception {

//        String str = null;
        channel.basicQos(5);//消费保障，QOS为1分钟内预取的最大消费数量
        System.out.println("队列： myQueue1 消费者1号 ："+num);
        try {
            Thread.currentThread().sleep(2000);
            if(num>=10){
                System.out.println("消费者1号 消费失败num =="+num);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);//不断重发
            }else{
                System.out.println("消费者1号 消费成功num =="+num);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("存储异常状态，转换队列，并且手动ACK");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        }
    }


}
