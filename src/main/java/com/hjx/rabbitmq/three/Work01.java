package com.hjx.rabbitmq.three;

import com.hjx.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author hjx
 * @date 2023/4/14 1:06
 */
public class Work01 {
    public static final String ACK_QUEUE_NAME = "MESSION";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1 等待接收消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            String msg = new String(message.getBody());
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(("接收到消息:"+msg));
            //手动应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        boolean autoAck = false;
        channel.basicConsume(ACK_QUEUE_NAME,autoAck,deliverCallback,(tag)->{
            System.out.println(tag+ "回调逻辑");
        });
    }
}
