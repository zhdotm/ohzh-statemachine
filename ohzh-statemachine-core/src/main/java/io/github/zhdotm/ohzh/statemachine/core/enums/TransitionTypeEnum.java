package io.github.zhdotm.ohzh.statemachine.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 转换类型
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum TransitionTypeEnum {
    /**
     * 转换类型
     */
    EXTERNAL("external", "外部转换"),
    INTERNAL("internal", "内部转换"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;

}
