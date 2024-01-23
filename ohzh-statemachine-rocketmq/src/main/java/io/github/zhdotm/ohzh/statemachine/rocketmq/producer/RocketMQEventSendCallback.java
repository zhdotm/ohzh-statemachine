package io.github.zhdotm.ohzh.statemachine.rocketmq.producer;

import io.github.zhdotm.ohzh.statemachine.mq.producer.IEventSendCallback;
import lombok.AllArgsConstructor;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class RocketMQEventSendCallback implements SendCallback {
    private final IEventSendCallback eventSendCallback;

    @Override
    public void onSuccess(SendResult sendResult) {
        eventSendCallback.onSuccess(RocketMQEventSendResult.create(sendResult));
    }

    @Override
    public void onException(Throwable throwable) {
        eventSendCallback.onException(throwable);
    }

}
