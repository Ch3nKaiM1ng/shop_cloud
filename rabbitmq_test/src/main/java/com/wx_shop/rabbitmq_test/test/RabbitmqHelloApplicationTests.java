package com.wx_shop.rabbitmq_test.test;

public class RabbitmqHelloApplicationTests {
    private static Send sender;

    public void hello() throws Exception {
        sender.send();
    }

    public static void main(String args[]){

        try {
            Send sender=new Send();
            Receiver receiver=new Receiver();
            sender.send();
        }catch (Exception e){

        }

    }
}
