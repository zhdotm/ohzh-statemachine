package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IStateContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.StateContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateRetContext;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class StateRetContextImpl<S, E> implements IStateRetContext<S, E> {

    private final StateContextImpl<S, E> stateContext;

    public static <S, E> StateRetContextImpl<S, E> getInstance(StateContextImpl<S, E> stateContext) {

        return new StateRetContextImpl<>(stateContext);
    }

    @Override
    public IStateContext<S, E> build() {

        return stateContext;
    }

}
