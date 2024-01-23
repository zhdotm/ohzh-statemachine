package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;

/**
 * @author zhihao.mao
 */

public interface IInternalTransitionPerformBuilder<S, E, C, A> {

    ITransition<S, E, C, A> build();
}
