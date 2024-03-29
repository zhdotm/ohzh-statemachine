package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.StateContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateContextBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateContextOnBuilder;

/**
 * @author zhihao.mao
 */

public class StateContextBuilderImpl<S, E> implements IStateContextBuilder<S, E> {

    public static <S, E> StateContextBuilderImpl<S, E> getInstance() {

        return new StateContextBuilderImpl<>();
    }

    @Override
    public IStateContextOnBuilder<S, E> on(IEventContext<S, E> eventContext) {
        StateContextImpl<S, E> stateContext = StateContextImpl.getInstance();
        stateContext.on(eventContext);

        return StateContextOnBuilderImpl.getInstance(stateContext);
    }

}
