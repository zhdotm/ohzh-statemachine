package io.github.zhdotm.ohzh.statemachine.core.support.factory;


import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IStateMachineBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl.StateMachineBuilderImpl;

/**
 * @author zhihao.mao
 */

public class StateMachineFactory {

    public static <M, S, E, C, A> IStateMachineBuilder<M, S, E, C, A> create() {

        return StateMachineBuilderImpl.getInstance();
    }

}
