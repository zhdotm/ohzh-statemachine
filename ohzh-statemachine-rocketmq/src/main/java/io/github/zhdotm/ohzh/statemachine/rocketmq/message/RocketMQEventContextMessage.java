package io.github.zhdotm.ohzh.statemachine.rocketmq.message;

import cn.hutool.json.JSONUtil;
import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author zhihao.mao
 */

public class RocketMQEventContextMessage extends EventContextMessage {

    public static RocketMQEventContextMessage create(MessageExt messageExt) {
        String messageExtBody = new String(messageExt.getBody());
        if (!JSONUtil.isTypeJSON(messageExtBody)) {

            return null;
        }
        return JSONUtil.toBean(messageExtBody, RocketMQEventContextMessage.class);
    }

}
