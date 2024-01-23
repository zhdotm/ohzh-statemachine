package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.ActionImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IInternalTransitionPerformBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IInternalTransitionWhenBuilder;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class InternalTransitionWhenBuilderImpl<S, E, C, A> implements IInternalTransitionWhenBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionWhenBuilderImpl<S, E, C, A> getInstance(@NonNull ITransition<S, E, C, A> transition) {
        InternalTransitionWhenBuilderImpl<S, E, C, A> transitionWhenBuilder = new InternalTransitionWhenBuilderImpl<>();
        transitionWhenBuilder.transition = transition;

        return transitionWhenBuilder;
    }

    @Override
    public IInternalTransitionPerformBuilder<S, E, C, A> perform(@NonNull A actionCode, @NonNull Function<Object[], Object> execute) {
        ActionImpl<A> action = ActionImpl.getInstance();
        action.actionCode(actionCode)
                .execute(execute);

        transition.perform(action);

        return InternalTransitionPerformBuilderImpl.getInstance(transition);
    }

}
