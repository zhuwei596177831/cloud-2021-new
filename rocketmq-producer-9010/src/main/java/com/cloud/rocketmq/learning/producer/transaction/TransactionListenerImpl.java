package com.cloud.rocketmq.learning.producer.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 朱伟伟
 * @date 2021-02-26 10:53:16
 * @description 实现事务的监听接口
 * 当发送半消息成功时，我们使用 executeLocalTransaction 方法来执行本地事务。它返回前一节中提到的三个事务状态之一。
 * <p>
 * checkLocalTransaction 方法用于检查本地事务状态，并回应消息队列的检查请求。它也是返回前一节中提到的三个事务状态之一。
 */
public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * @param msg:
     * @param arg:
     * @author: 朱伟伟
     * @date: 2021-02-26 14:22
     * @description: When send transactional prepare(half) message succeed, this method will be invoked to execute local transaction.
     * 发送半消息成功后 broker回调此方法  执行本地事务
     * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
     * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
     * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
     **/
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println("msg:" + msg);
        System.out.println("arg:" + arg);
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(msg.getTransactionId(), status);
        return LocalTransactionState.UNKNOW;
    }

    /**
     * @param messageExt:
     * @author: 朱伟伟
     * @date: 2021-02-26 14:25
     * @description: When no response to prepare(half) message. broker will send check message to check the transaction status,
     * and this method will be invoked to get local transaction status.
     * {@link #executeLocalTransaction(Message, Object)}返回中间状态时  broker回调此方法检查事务状态
     **/
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("messageExt:" + messageExt);
        Integer status = localTrans.get(messageExt.getTransactionId());
        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
