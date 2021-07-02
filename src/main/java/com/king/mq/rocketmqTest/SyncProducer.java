package com.king.mq.rocketmqTest;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by ljq on 2020-12-17 17:30
 * 生产者
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception{
        final DefaultMQProducer producer = new DefaultMQProducer("test_producer");
        //设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        //一直发消息，可以设置为多线程跑
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @SneakyThrows
                @Override
                public void run() {
                    while (true) {
                        Message msg = new Message("TopicTest", "TagA", ("Test").getBytes(RemotingHelper.DEFAULT_CHARSET));
                        SendResult send = producer.send(msg);
                    }
                }
            }.start();
        }
        //程序卡在这里，不结束，就不停发消息
        while (true) {
            Thread.sleep(1000);
        }
    }
}
