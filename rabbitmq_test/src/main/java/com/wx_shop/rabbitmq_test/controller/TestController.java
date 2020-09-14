package com.wx_shop.rabbitmq_test.controller;


import com.alibaba.fastjson.JSONObject;
import com.wx_shop.rabbitmq_test.utils.JedisUtils;
import com.wx_shop.rabbitmq_test.utils.RedisLock;
import com.wx_shop.rabbitmq_test.utils.ReturnDiscern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("testCenter")
public class TestController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private RedisLock redisLock;


    Map<String,Object> map=new HashMap<>();

    private ReturnDiscern re =new ReturnDiscern();

    int count = 0;
    /**
     {
     "queue":"BA",
     "data":{
     "name":"chen"
     }
     }
     * **/

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping("sendQueue")
    public Map<String, Object> sendQueue2(@RequestBody JSONObject jsonObject) {
        String queue=jsonObject.getString("queue");//队列名称
        String data=jsonObject.getString("data");//数据
        int runNum=jsonObject.getInteger("runNum");//执行数量
        for(int i=0;i<runNum;i++){
            rabbitTemplate.convertAndSend(queue,i);
        }
        return re.SUCCESS();
    }

    @RequestMapping("testPutJedis")
    public Map<String, Object> testPutJedis(@RequestBody JSONObject jsonObject) {
        String dataName=jsonObject.getString("dataName");
        String dataValue=jsonObject.getString("dataValue");
        JedisUtils.set(dataName,dataValue);
        return re.SUCCESS();
    }

    @RequestMapping("testJedis")
    public Map<String, Object> testJedis(@RequestBody JSONObject jsonObject) {
        Jedis jedis=new Jedis("47.107.47.13",6379);

        String dataName=jsonObject.getString("dataName");
        String dataValue=jsonObject.getString("dataValue");

        jedis.set(dataName,dataValue);
        return re.SUCCESS();
    }

    @RequestMapping("testExpireJedis")
    public Map<String, Object> testExpireJedis(@RequestBody JSONObject jsonObject) {
        Jedis jedis=new Jedis("47.107.47.13",6379);

        String dataName=jsonObject.getString("dataName");
        String dataValue=jsonObject.getString("dataValue");
        jedis.set(dataName,dataValue);
        jedis.expire(dataName,300);
        return re.SUCCESS();
    }

    @RequestMapping("testLockJedis")
    public Map<String, Object> testLockJedis (@RequestBody JSONObject jsonObject) throws InterruptedException {
        int clientcount =1000;

        CountDownLatch countDownLatch = new CountDownLatch(clientcount);

        ExecutorService executorService = Executors.newFixedThreadPool(clientcount);
        long start = System.currentTimeMillis();
        for (int i = 0;i<clientcount;i++){
            executorService.execute(() -> {

                //通过Snowflake算法获取唯一的ID字符串
                String id = jsonObject.getString("id");
                try {
                    redisLock.lock(id);
                    count++;
                }finally {
                    redisLock.unlock(id);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        logger.info("执行线程数:{},总耗时:{},count数为:{}",clientcount,end-start,count);
        return re.SUCCESS();
    }



    @RequestMapping("testGetJedis")
    public Map<String, Object> testGetJedis(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        Jedis jedis=new Jedis("47.107.47.13");
        String header=request.getHeader("data");
        logger.info("data="+jsonObject);
        logger.info("header="+header);
        String dataName=jsonObject.getString("dataName");
        String databack=jedis.get(dataName);
        return re.SUCCESSOBJ(databack);
    }
/**
 {
 "exchange":"fanoutExchange",
 "routingKey":"",
 "data":{
 "name":"chen"
 }
 }
 * **/
    @RequestMapping("sendByExchangeAndRoutingKey")
    public Map<String, Object> sendQueue32(@RequestBody JSONObject jsonObject) {
        String exchange= jsonObject.getString("exchange");
        String routingKey=jsonObject.getString("routingKey");
        JSONObject data= jsonObject.getJSONObject("data");
        rabbitTemplate.convertAndSend(exchange,routingKey,data);
        return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("delaySendMessage")
    public Map<String, Object> delaySendMessage(@RequestBody JSONObject jsonObject) {
        String exchange= jsonObject.getString("exchange");
        String routingKey=jsonObject.getString("routingKey");
        String data= jsonObject.getString("data");
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setExpiration("10000");
        messageProperties.setCorrelationId(UUID.randomUUID().toString());
        Message message=new Message(data.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
        return re.SUCCESS();
    }
}
