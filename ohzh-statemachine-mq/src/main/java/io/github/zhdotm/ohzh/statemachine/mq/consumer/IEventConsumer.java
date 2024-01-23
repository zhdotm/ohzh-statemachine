package io.github.zhdotm.ohzh.statemachine.mq.consumer;


import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;

/**
 * @author zhihao.mao
 */

public interface IEventConsumer {

    /**
     * 消费消息
     *
     * @param message 消息
     */
    void consume(EventContextMessage message);

}
