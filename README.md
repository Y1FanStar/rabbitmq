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
## 2023年4月17日 
### 持久化 简单工作模式
#### 队列持久化
- 当信道已被为不可为持久化之后 不可再表面可以持久化 会报错
-  channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'durable' for queue 'MESSION' in vhost '/': received 'true' but current is 'false', class-id=50, method-id=10)
- 处理办法删除当前信道
#### 消息持久化
MessageProperties.PERSISTENT_TEXT_PLAIN 使发出的消息持久化
当消息并未完全存入磁盘的时候发生异常还是会有消息的丢失的可能
  - 消息不公平分分发 充分利用 性能让可以多处理的消费者处理更多信息   
    //使用不公平分发（预取值个数）
    int basicQos = 1;
    channel.basicQos(basicQos);
## 发布确认
    开启发布确认
    channel.confirmSelect();
-   单个发布确认 可确认发布错误的消息队列位置
-  批量发布确认
### 异步发布确认
较好的利用资源、更快、但是代码更难实现
发布者资源后 利用一个 线程多可访问的map来处理
## 发布订阅模式
