package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;

/**
 * @author zhihao.mao
 */

public interface IInternalTransitionFromBuilder<S, E, C, A> {

    IInternalTransitionOnBuilder<S, E, C, A> on(E eventCode);

}
