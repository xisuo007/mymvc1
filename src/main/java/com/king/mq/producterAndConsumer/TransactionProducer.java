package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.*;

/**
 * Created by ljq on 2020-12-29 19:34
 * half 事务消息发送端
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception{
        //构建一个回调函数
        TransactionListener listener = new TransactionListenerImpl();
        //创建一个支持事务消息的Producer     指定一个生产者分组
        TransactionMQProducer producer = new TransactionMQProducer("TestProducerGroup");
        //构建线程池，用来处理回调
        ExecutorService executor = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("TestThread");
                return thread;
            }
        });
        //给事务消息生产者设置对应的线程池，负责执行RocketMQ回调请求
        producer.setExecutorService(executor);
        //给事务消息生产者设置对应的回调函数
        producer.setTransactionListener(listener);

        producer.start();
        //构建消息，指定Topic是谁
        Message msg = new Message("PayOrderSuccessTopic", "TestTag", "TestKey", ("订单支付消息").getBytes(RemotingHelper.DEFAULT_CHARSET));
        try {
            //将消息作为half消息的模式发出
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
        } catch (MQClientException e) {
            //half消息发送失败   进行回滚逻辑(退款，跟新订单为关闭)
            e.printStackTrace();
        }
    }
}
