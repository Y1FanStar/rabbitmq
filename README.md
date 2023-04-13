# 个人rabbitMq学习
## Lunix中mq的安装
 - elong的安装
 - 前置安装等等

一些命令
````
    找到安装位置 
    whereis rabbitmq
    进入后设置为后台运行  
    ./rabbitmq-server -detached 
    systemctl stop firewalld.service 关闭防火墙
````
## 2023年4月13日
 - 完成虚拟机的安装、lunix中 mq的服务启动
 - java环境搭建 创建一个消费者、一个生产者
## 2023年4月14日
测试工作队列
 mq会将生产者产生的消息 轮流发给已连接的消费者
 一条消息只会被使用一次

### 消息应答
告诉 rabbitmq 它已经处理了，rabbitmq 可以把该消息删除了。
- 自动应答
    消费者一旦接收到消息就 返回处理完成
    实际情况会有代码处理（不推荐）
- 手动应答
    使用三种方法进行手动应答、并且可以进行批量应答减少网络压力 不建议进行批量应答
  - Channel.basicAck(用于肯定确认) 处理成功 进行丢弃
  - Channel.basicNack(用于否定确认) 未处理完成
  - Channel.basicReject(用于否定确认) 不处理消息 直接丢弃
    