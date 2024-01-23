package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface IExternalTransitionWhenBuilder<S, E, C, A> {

    IExternalTransitionPerformBuilder<S, E, C, A> perform(A actionCode, Function<Object[], Object> doSomething);
}
