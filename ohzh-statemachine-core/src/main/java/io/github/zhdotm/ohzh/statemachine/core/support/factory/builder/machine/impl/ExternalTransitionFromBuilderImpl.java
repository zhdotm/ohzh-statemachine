package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionFromBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionOnBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionFromBuilderImpl<S, E, C, A> implements IExternalTransitionFromBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionFromBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionFromBuilderImpl<S, E, C, A> transitionFromBuilder = new ExternalTransitionFromBuilderImpl<>();

        transitionFromBuilder.transition = transition;

        return transitionFromBuilder;
    }

    @Override
    public IExternalTransitionOnBuilder<S, E, C, A> on(@NonNull E eventCode) {
        transition.on(eventCode);

        return ExternalTransitionOnBuilderImpl.getInstance(transition);
    }

}
