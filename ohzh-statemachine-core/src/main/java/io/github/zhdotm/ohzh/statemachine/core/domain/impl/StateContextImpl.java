package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateContext;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class StateContextImpl<S, E> implements IStateContext<S, E> {

    @Getter
    private S stateCode;

    @Getter
    private Object payload;

    @Getter
    private IEventContext<S, E> eventContext;

    public static <S, E> StateContextImpl<S, E> getInstance() {

        return new StateContextImpl<>();
    }

    public StateContextImpl<S, E> to(@NonNull S stateCode) {
        this.stateCode = stateCode;

        return this;
    }

    public StateContextImpl<S, E> ret(Object payload) {
        this.payload = payload;

        return this;
    }

    public StateContextImpl<S, E> on(@NonNull IEventContext<S, E> eventContext) {
        this.eventContext = eventContext;

        return this;
    }

}
