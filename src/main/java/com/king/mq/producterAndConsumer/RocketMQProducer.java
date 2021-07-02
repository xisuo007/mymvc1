package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by ljq on 2020-12-21 17:21
 * RocketMQ的生产者类，发送消息用
 */
public class RocketMQProducer {
    private static DefaultMQProducer producer;
    static {
        //这里构建一个Producer实例对象
        producer = new DefaultMQProducer("order_producer_group");
        //设置Producer的NameServer的地址，让他可以拉取路由信息，知道每个Topic的数据分散在哪些Broker上，方便发消息到Broker上去
        producer.setNamesrvAddr("localhost:9876");
        //启动一个Producer
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public static void send(String topic,String msg) throws Exception {
        //构建一条消息
        Message message = new Message(topic, "", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        //1.利用Producer发送消息，同步发送，会等待返回结果
        SendResult send = producer.send(message);

        //2.发送单向消息，发送之后不关成功失败
        producer.sendOneway(message);

        //3.发送异步消息，消息结果进行回调
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                //消息发送成功时的回调处理
            }

            @Override
            public void onException(Throwable throwable) {
                //消息失败时的回调处理
            }
        });
        System.out.println(send);
    }
}
