package com.wx_shop.rabbitmq_test.test;

import org.junit.Ignore;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Ignore
public class Send {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String context = "hello " + simpleDateFormat.format(new Date());
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("myQueue2", context);
    }
}
