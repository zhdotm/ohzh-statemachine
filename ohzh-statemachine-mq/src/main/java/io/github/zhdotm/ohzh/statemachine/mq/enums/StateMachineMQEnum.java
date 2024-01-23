package io.github.zhdotm.ohzh.statemachine.mq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum StateMachineMQEnum {

    /**
     * 状态机消息队列枚举 尾缀
     */
    STATE_MACHINE_CODE("stateMachineCode", "状态机编码"),
    STATE_CODE("stateCode", "状态编码"),
    EVENT_CODE("eventCode", "事件编码"),
    TOPIC_NAME_SPACE("ohzh_statemachine", "命名空间"),
    TAGS("tags", "tags"),
    TAG_STATE_CONCAT_EVENT("_onEvent_", "tag连接符"),
    KEYS("keys", "keys"),
    EVENT_SOURCE("event_source", "事件来源"),
    PAYLOAD_CLAZZ_NAME_ARRAY("payload_clazz_name_array", "负载数据类名数组"),
    PRODUCER_GROUP_NAME_SUFFIX("_producer_group", "生产者组名尾缀"),
    CONSUMER_GROUP_NAME_SUFFIX("_consumer_group", "消费者组名尾缀"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;

}
