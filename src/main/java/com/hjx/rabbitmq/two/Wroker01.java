package com.hjx.rabbitmq.two;

import com.hjx.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author hjx
 * @date 2023/4/14 0:21
 */
public class Wroker01 {
    public static final String QUEUE_NAME = "HELLO";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明式接受消息
        DeliverCallback deliverCallback =(consumerTag, msg)->{
            System.out.println("接受到的消息内容："+new String(msg.getBody()));
        };

        // 声明式取消消息回调
        CancelCallback cancelCallback =(consumerTag)->{
            System.out.println("被中断");
        };
        System.out.println("消费者3准备接收......");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
