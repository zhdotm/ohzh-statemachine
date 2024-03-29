package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;


import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;

import java.util.List;

/**
 * @author zhihao.mao
 */

public interface IStateMachineBuilder<M, S, E, C, A> {

    /**
     * 添加转换
     *
     * @param transitions 转换
     */
    void transitions(List<ITransition<S, E, C, A>> transitions);

    /**
     * 创建外部转换构建器
     *
     * @return 外部转换构建器
     */
    IExternalTransitionBuilder<S, E, C, A> createExternalTransition();

    /**
     * 创建内部转换构建器
     *
     * @return 内部转换构建器
     */
    IInternalTransitionBuilder<S, E, C, A> createInternalTransition();

    /**
     * 构件状态机
     *
     * @param stateMachineCode 状态机ID
     * @return 状态机
     */
    IStateMachine<M, S, E, C, A> build(M stateMachineCode);

}
