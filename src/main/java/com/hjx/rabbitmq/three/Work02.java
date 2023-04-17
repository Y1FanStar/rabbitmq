package com.hjx.rabbitmq.three;

import com.hjx.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author hjx
 * @date 2023/4/14 1:07
 */
public class Work02 {
    public static final String ACK_QUEUE_NAME = "MESSION";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2 等待接收消息处理时间较短");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            String msg = new String(message.getBody());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(("接收到消息:"+msg));
            //手动应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };

        //使用不公平分发
        int basicQos = 2;
        channel.basicQos(basicQos);
        boolean autoAck = false;
        channel.basicConsume(ACK_QUEUE_NAME,autoAck,deliverCallback,(tag)->{
            System.out.println(tag+ "回调逻辑");
        });
    }
}
