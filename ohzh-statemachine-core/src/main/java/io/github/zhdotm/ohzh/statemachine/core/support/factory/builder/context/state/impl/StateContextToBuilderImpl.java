package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.impl.StateContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateContextToBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateRetContext;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class StateContextToBuilderImpl<S, E> implements IStateContextToBuilder<S, E> {

    private final StateContextImpl<S, E> stateContext;

    public static <S, E> StateContextToBuilderImpl<S, E> getInstance(StateContextImpl<S, E> stateContext) {

        return new StateContextToBuilderImpl<>(stateContext);
    }

    @Override
    public IStateRetContext<S, E> ret(Object obj) {
        stateContext.ret(obj);

        return StateRetContextImpl.getInstance(stateContext);
    }

}
