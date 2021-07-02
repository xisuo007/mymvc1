package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Created by ljq on 2020-12-30 16:51
 * 保证顺序性的提供者
 */
public class SequenceProducer {

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

    public static void send(String topic,String msg,Integer orderId) throws Exception {
        //构建一条消息
        Message message = new Message(topic, "", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));

        Message message2 = new Message(topic,//Topic名称
                "tableName", //这条数据的tag
                msg.getBytes(RemotingHelper.DEFAULT_CHARSET));//数据内容
        message2.putUserProperty("a","111");//给一条消息设置一些属性
        message2.putUserProperty("b","222");

        /**
         * 如果想发送延迟消息就需要设置延迟级别----1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
         */
        message.setDelayTimeLevel(3);//10s

        //发送消息然后根据订单id来取模固定放入某个MQ中
        SendResult send = producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % list.size();
                return list.get(index);
            }
        }, orderId);
        System.out.println(send);
    }
}
