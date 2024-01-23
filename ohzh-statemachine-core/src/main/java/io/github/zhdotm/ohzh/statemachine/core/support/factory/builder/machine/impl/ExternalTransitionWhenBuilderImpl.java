package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.ActionImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionPerformBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionWhenBuilder;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionWhenBuilderImpl<S, E, C, A> implements IExternalTransitionWhenBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionWhenBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {
        ExternalTransitionWhenBuilderImpl<S, E, C, A> transitionWhenBuilder = new ExternalTransitionWhenBuilderImpl<>();
        transitionWhenBuilder.transition = transition;

        return transitionWhenBuilder;
    }

    @Override
    public IExternalTransitionPerformBuilder<S, E, C, A> perform(@NonNull A actionCode, @NonNull Function<Object[], Object> execute) {
        ActionImpl<A> action = ActionImpl.getInstance();
        action.actionCode(actionCode)
                .execute(execute);

        transition.perform(action);

        return ExternalTransitionPerformBuilderImpl.getInstance(transition);
    }

}
