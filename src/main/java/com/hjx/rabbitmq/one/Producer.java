package com.hjx.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 发消息给队列
 * @author hjx
 * @date 2023/4/13 0:05
 */
public class Producer {
    public static final String QUEUE_NAME = "HELLO";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.231.128");
        factory.setUsername("admin");
        factory.setPassword("admin123");


        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            HashMap<Object, Object> map = new HashMap<>();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String msg = "hello world!!";
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
