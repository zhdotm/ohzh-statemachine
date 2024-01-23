package io.github.zhdotm.ohzh.statemachine.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * plantUml文件枚举
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum PlantUmlFileEnum {

    /**
     * 文件尾缀
     */
    FILE_SUFFIX(".puml"),
    ;
    @Getter
    private final String value;
}
