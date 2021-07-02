package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by ljq on 2020-12-21 17:35
 * 消费者实例对象-等待推动模式
 */
public class RocketMQConsumerPush {
    public static void start() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //"credit_group"是消费者分组，不同的系统进行区分
                    //DefaultMQPushConsumer用的是Push的消费模式，被动接收Broker推送过来的消息
                    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("credit_group");
                    //设置NameServer地址，方便拉取broker上的topic中的数据
                    consumer.setNamesrvAddr("localhost:9876");
                    //订阅某个Topic里的消息，拉取进行消费
                    consumer.subscribe("TopicOrderPaySuccess", "*");
                    //注册消息监听器来处理拉取到的订单消息，拉取到了就会回调这个方法进行处理
                    consumer.registerMessageListener(new MessageListenerConcurrently() {
                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                        ConsumeConcurrentlyContext context) {
                            //这里对获取的msgs订单消息进行处理，不同系统进行不同逻辑处理(增加积分，发优惠券，通知发货等)
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    });
                    //启动消费者实例
                    consumer.start();
                    System.out.println("Consumer started");
                    while (true) {
                        //线程不退出
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
