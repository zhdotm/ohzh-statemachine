package io.github.zhdotm.ohzh.statemachine.starter.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum StateMachineScopeEnum {

    /**
     * 状态机作用范围
     */
    LOCAL("local", "本地"),
    REMOTE("remote", "远程（也可调用本地）"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;

}
