package com.hjx.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接收消息
 * @author hjx
 * @date 2023/4/13 0:49
 */
public class Concummer {
    public static final String QUEUE_NAME = "HELLO";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.231.128");
        factory.setUsername("admin");
        factory.setPassword("admin123");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //声明式接受消息
            DeliverCallback deliverCallback =(consumerTag,msg)->{
                System.out.println(new String(msg.getBody()));
            };

            // 声明式取消消息回调
            CancelCallback cancelCallback =(consumerTag)->{
                System.out.println("被中断");
            };
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
