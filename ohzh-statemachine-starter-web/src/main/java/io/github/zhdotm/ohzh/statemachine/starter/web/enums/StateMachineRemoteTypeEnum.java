package io.github.zhdotm.ohzh.statemachine.starter.web.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum StateMachineRemoteTypeEnum {

    /**
     * 状态机远程类型
     */
    ROECKTMQ("roecktmq", "roecktmq"),
    KAFKA("kafka", "kafka"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;

    public static StateMachineRemoteTypeEnum getByCode(String code) {
        if (StrUtil.isBlank(code)) {

            return null;
        }

        for (StateMachineRemoteTypeEnum stateMachineRemoteTypeEnum : StateMachineRemoteTypeEnum.values()) {
            if (stateMachineRemoteTypeEnum.getCode().equalsIgnoreCase(code)) {

                return stateMachineRemoteTypeEnum;
            }
        }

        return null;
    }

}
