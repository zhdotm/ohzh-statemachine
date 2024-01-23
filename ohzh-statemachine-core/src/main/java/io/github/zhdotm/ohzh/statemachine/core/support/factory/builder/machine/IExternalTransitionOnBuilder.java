package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface IExternalTransitionOnBuilder<S, E, C, A> {

    IExternalTransitionWhenBuilder<S, E, C, A> when(C conditionCode, Function<IEventContext<S, E>, Boolean> check);

}
