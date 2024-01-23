package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionToBuilder;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionToBuilderImpl<S, E, C, A> implements IExternalTransitionToBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionToBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {
        ExternalTransitionToBuilderImpl<S, E, C, A> transitionToBuilder = new ExternalTransitionToBuilderImpl<>();
        transitionToBuilder.transition = transition;

        return transitionToBuilder;
    }

    @Override
    public ITransition<S, E, C, A> build() {

        return transition;
    }

}
