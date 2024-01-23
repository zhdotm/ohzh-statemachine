package io.github.zhdotm.ohzh.statemachine.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum ContextEnum {

    /**
     * 上下文枚举
     */
    APP_NAME("appName", "应用名称"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;

}
