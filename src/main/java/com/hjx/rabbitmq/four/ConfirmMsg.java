package com.hjx.rabbitmq.four;

import com.hjx.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

/**
 * 发布确认类
 * @author hjx
 * @date 2023/4/17 22:50
 */
public class ConfirmMsg {
    private static  final int MESSAGE_COUNT= 1000;
    public static void main(String[] args) {
        ConfirmMsg.pushMessage(); //发布1000个单独确认消息,耗时621ms
//        ConfirmMsg.pushMsgBatch(); //发布1000个单独确认消息,耗时112ms
    }


    /**
     * 单个发布确认下
     */
    public static void pushMessage(){
        try{
            Channel channel = RabbitMqUtils.getChannel();
            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, false, false, false, null);
            //开启发布确认
            channel.confirmSelect();
            long begin = System.currentTimeMillis();
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String message = i + "";
                channel.basicPublish("", queueName, null, message.getBytes());
                //服务端返回 false 或超时时间内未返回，生产者可以消息重发
                boolean flag = channel.waitForConfirms();
                if(flag){
//                    System.out.println("消息发送成功");
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息,耗时" + (end - begin) +
                    "ms");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量发布确认
     */
    public static void pushMsgBatch(){
        try  {
            Channel channel = RabbitMqUtils.getChannel();
            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, false, false, false, null);
            //开启发布确认
            channel.confirmSelect();
            //批量确认消息大小
            int batchSize = 100;
            //未确认消息个数
            int outstandingMessageCount = 0;
            long begin = System.currentTimeMillis();
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String message = i + "";
                channel.basicPublish("", queueName, null, message.getBytes());
                outstandingMessageCount++;
                if (outstandingMessageCount == batchSize) {

                    channel.waitForConfirms();
                    outstandingMessageCount = 0;
                }
            }
            //为了确保还有剩余没有确认消息 再次确认
            if (outstandingMessageCount > 0) {
                channel.waitForConfirms();
            }
            long end = System.currentTimeMillis();
            System.out.println("发布" + MESSAGE_COUNT + "个批量确认消息,耗时" + (end - begin) +
                    "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
