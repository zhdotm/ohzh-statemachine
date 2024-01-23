/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.zhdotm.ohzh.statemachine.mq.message;

import cn.hutool.core.util.SystemPropsUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.enums.ContextEnum;
import io.github.zhdotm.ohzh.statemachine.mq.enums.StateMachineMQEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zhihao.mao
 */

@Data
@Accessors(chain = true)
public class EventContextMessage implements Serializable {

    /**
     * 主题
     */
    private String topic;

    /**
     * 消息体
     */
    private String body;

    /**
     * 额外属性
     */
    private Map<String, String> extraProperties;

    public static EventContextMessage create(String stateMachineCode, IEventContext<String, String> eventContext) {
        String stateCode = eventContext.getStateCode();
        IEvent<String> event = eventContext.getEvent();
        String eventCode = event.getEventCode();
        String tags = stateCode + StateMachineMQEnum.TAG_STATE_CONCAT_EVENT.getCode() + eventCode;
        Map<String, String> extraProperties = Optional.ofNullable(event.getExtraProperties())
                .orElse(new HashMap<>());
        String body = null;
        Object[] payload = event.getPayload();
        if (payload != null && payload.length != 0) {
            List<String> payloadClazzNames = Arrays.stream(payload)
                    .map(obj -> obj.getClass().getName())
                    .collect(Collectors.toList());
            extraProperties.put(StateMachineMQEnum.PAYLOAD_CLAZZ_NAME_ARRAY.getCode(), JSONUtil.toJsonStr(payloadClazzNames));
            body = JSONUtil.toJsonStr(payload);
        }

        String applicationName = SpringUtil.getApplicationName();
        extraProperties.put(StateMachineMQEnum.EVENT_SOURCE.getCode(), applicationName);
        SystemPropsUtil.set(ContextEnum.APP_NAME.getCode(), applicationName);

        extraProperties.put(StateMachineMQEnum.STATE_MACHINE_CODE.getCode(), stateMachineCode);
        extraProperties.put(StateMachineMQEnum.STATE_CODE.getCode(), stateCode);
        extraProperties.put(StateMachineMQEnum.EVENT_CODE.getCode(), eventCode);
        extraProperties.put(StateMachineMQEnum.TAGS.getCode(), tags);
        extraProperties.put(StateMachineMQEnum.KEYS.getCode(), tags);

        return new EventContextMessage()
                .setTopic(stateMachineCode)
                .setBody(body)
                .setExtraProperties(extraProperties);
    }

}
