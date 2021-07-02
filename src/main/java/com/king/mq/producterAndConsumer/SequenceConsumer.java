package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Created by ljq on 2020-12-30 16:51
 * 保证顺序性的消费者
 */
public class SequenceConsumer {
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

                    consumer.subscribe("TopicOrderPaySuccess", "tableName1 || tableName2");//对发送消息的tag进行过滤
                    consumer.subscribe("TopicOrderPaySuccess", MessageSelector.bySql("a>5 AND b = '222'"));//也可以对消息的属性值进行过滤
                    //注册消息监听器来处理拉取到的订单消息，拉取到了就会回调这个方法进行处理
                    //MessageListenerOrderly是只让一个线程来处理其中的消息
                    consumer.registerMessageListener(new MessageListenerOrderly() {
                        @Override
                        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                            context.setAutoCommit(true);
                            try {
                                for (MessageExt msg : msgs) {
                                    //处理有序的消息
                                }
                                return ConsumeOrderlyStatus.SUCCESS;
                            } catch (Exception e) {
                                //消息处理异常，返回状态，让它等一会再继续处理这批消息
                                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                            }
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
