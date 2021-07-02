package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.Set;

/**
 * Created by ljq on 2020-12-21 17:35
 * 消费者实例对象-主动拉取模式
 */
public class RocketMQConsumerPull {
    public static void start() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //"credit_group"是消费者分组，不同的系统进行区分
                    //DefaultMQPullConsumer用的是Pull的消费模式，主动拉取Broker的消息进行消费
                    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("credit_group");
                    //设置NameServer地址，方便拉取broker上的topic中的数据
                    consumer.setNamesrvAddr("localhost:9876");
                    consumer.start();
                    //订阅某个Topic里的消息，拉取进行消费
                    Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicOrderPaySuccess");
                    for (MessageQueue mq : mqs) {
                        System.out.println("Consumer from the queue:"+mq.toString());
                        SINGLE_MQ:
                        while (true) {
                            //PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                            //System.out.println(pullResult);

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
