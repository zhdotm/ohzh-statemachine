package io.github.zhdotm.ohzh.statemachine.rocketmq.producer;

import io.github.zhdotm.ohzh.statemachine.mq.producer.EventSendResult;
import lombok.experimental.Accessors;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * @author zhihao.mao
 */

@Accessors(chain = true)
public class RocketMQEventSendResult extends EventSendResult {

    public static RocketMQEventSendResult create(SendResult sendResult) {
        RocketMQEventSendResult rocketMQEventSendResult = new RocketMQEventSendResult();
        rocketMQEventSendResult.setTopic(sendResult.getMessageQueue().getTopic())
                .setPartition(sendResult.getMessageQueue().getQueueId())
                .setMessageId(sendResult.getMsgId())
                .setOffset(sendResult.getQueueOffset());

        return rocketMQEventSendResult;
    }

}
