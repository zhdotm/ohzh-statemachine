package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface IInternalTransitionWhenBuilder<S, E, C, A> {

    IInternalTransitionPerformBuilder<S, E, C, A> perform(A actionCode, Function<Object[], Object> execute);
}
