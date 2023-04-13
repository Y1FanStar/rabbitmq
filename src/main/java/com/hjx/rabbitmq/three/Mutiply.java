package com.hjx.rabbitmq.three;

import com.hjx.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author hjx
 * @date 2023/4/14 1:00
 */
public class Mutiply {
    public static final String ACK_QUEUE_NAME = "MESSION";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(ACK_QUEUE_NAME,false,false,false,null);
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入信息");
        while (sc.hasNext()) {
            String message = sc.nextLine();
            channel.basicPublish("", ACK_QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息" + message);
        }
    }
}
