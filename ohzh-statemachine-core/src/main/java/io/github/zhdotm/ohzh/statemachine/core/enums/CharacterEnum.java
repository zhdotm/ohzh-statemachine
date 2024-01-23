package io.github.zhdotm.ohzh.statemachine.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字符常量
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum CharacterEnum {

    /**
     * 符号
     */
    HASH_TAG("#"),
    EMPTY(""),
    LEFT_SLASH("/"),
    RIGHT_SLASH("\\"),
    PERCENT_SIGN("%"),
    ;

    @Getter
    private final String value;

}
