package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionPerformBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionPerformBuilderImpl<S, E, C, A> implements IExternalTransitionPerformBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionPerformBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionPerformBuilderImpl<S, E, C, A> toBuilder = new ExternalTransitionPerformBuilderImpl<>();
        toBuilder.transition = transition;

        return toBuilder;
    }

    @Override
    public ExternalTransitionToBuilderImpl<S, E, C, A> to(@NonNull S stateCode) {
        transition.to(stateCode);

        return ExternalTransitionToBuilderImpl.getInstance(transition);
    }

}
