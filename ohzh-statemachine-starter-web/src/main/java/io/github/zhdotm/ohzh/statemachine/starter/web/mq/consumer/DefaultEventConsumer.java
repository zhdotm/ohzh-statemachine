package io.github.zhdotm.ohzh.statemachine.starter.web.mq.consumer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import io.github.zhdotm.ohzh.statemachine.mq.consumer.IEventConsumer;
import io.github.zhdotm.ohzh.statemachine.mq.enums.StateMachineMQEnum;
import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;
import io.github.zhdotm.ohzh.statemachine.starter.web.support.StateMachineSupport;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @author zhihao.mao
 */

public class DefaultEventConsumer implements IEventConsumer {

    @SneakyThrows
    @Override
    public void consume(EventContextMessage message) {
        if (ObjectUtil.isEmpty(message)) {
            return;
        }
        Map<String, String> extraProperties = message.getExtraProperties();
        String stateMachineCode = extraProperties.get(StateMachineMQEnum.STATE_MACHINE_CODE.getCode());
        String stateCode = extraProperties.get(StateMachineMQEnum.STATE_CODE.getCode());
        String eventCode = extraProperties.get(StateMachineMQEnum.EVENT_CODE.getCode());
        String payloadClazzNameArrayStr = extraProperties.get(StateMachineMQEnum.PAYLOAD_CLAZZ_NAME_ARRAY.getCode());
        if (StrUtil.isBlank(payloadClazzNameArrayStr)) {
            StateMachineSupport.fireEvent(stateMachineCode, stateCode, eventCode);
        }
        String bodyStr = message.getBody();
        JSONArray bodyArray = JSONUtil.parseArray(bodyStr);
        JSONArray payloadClazzNameArray = JSONUtil.parseArray(payloadClazzNameArrayStr);
        Object[] payload = new Object[payloadClazzNameArray.size()];
        for (int i = 0; i < payloadClazzNameArray.size(); i++) {
            String payloadClazzName = payloadClazzNameArray.get(i, String.class);
            Class<?> clazz = Class.forName(payloadClazzName);
            payload[i] = bodyArray.get(i, clazz);
        }
        StateMachineSupport.fireEvent(stateMachineCode, stateCode, eventCode, payload);
    }

}
