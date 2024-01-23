package io.github.zhdotm.ohzh.statemachine.mq.producer;


import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;

/**
 * @author zhihao.mao
 */

public interface IEventProducer {

    /**
     * 同步发送消息
     *
     * @param message 消息
     */
    EventSendResult syncSend(EventContextMessage message);

    /**
     * 异步发送消息
     *
     * @param message           消息
     * @param eventSendCallback 回调
     */
    void asyncSend(EventContextMessage message, IEventSendCallback eventSendCallback);

}
