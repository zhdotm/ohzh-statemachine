package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionFromBuilder;
import lombok.NonNull;

import java.util.Arrays;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionBuilderImpl<S, E, C, A> implements IExternalTransitionBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionBuilderImpl<S, E, C, A> transitionBuilder = new ExternalTransitionBuilderImpl<>();
        transitionBuilder.transition = transition;

        return transitionBuilder;
    }

    @Override
    public IExternalTransitionBuilder<S, E, C, A> sort(@NonNull Integer sort) {
        transition.sort(sort);

        return this;
    }

    @Override
    public IExternalTransitionFromBuilder<S, E, C, A> from(@NonNull S... stateCodes) {
        transition.from(Arrays.asList(stateCodes));

        return ExternalTransitionFromBuilderImpl.getInstance(transition);
    }

}
