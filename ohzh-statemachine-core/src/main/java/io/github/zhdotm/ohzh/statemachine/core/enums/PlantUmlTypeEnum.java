package io.github.zhdotm.ohzh.statemachine.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * uml类型
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum PlantUmlTypeEnum {

    /**
     * uml图类型
     */
    STATE_DIAGRAM("state_diagram", "状态图"),
    SEQUENCE_DIAGRAM("sequence_diagram", "时序图"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;
}
