package com.king.mq.producterAndConsumer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by ljq on 2020-12-29 19:37
 * 自定义回调逻辑
 */
public class TransactionListenerImpl implements TransactionListener {
    /**
     * half消息成功会回调此函数
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        //执行订单本地事务
        //根据后面执行结果，选择执行commit或rollback
        try {
            //本地都执行成功，返回commit
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            //本地事务执行失败，回滚    返回rollback，标记half消息无效
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * half消息发生错误没有commit或进行callback时调用此函数
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        //查询本地事务，是否执行成功了
        //Integer status = localTrans.get(messageExt.getTransactionId());
        Integer status = 0;
        //根据本地事务的情况选择执行commit或rollback
        if (status != null) {
            switch (status){
                case 0:return LocalTransactionState.UNKNOW;
                case 1:return LocalTransactionState.COMMIT_MESSAGE;
                case 2:return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
