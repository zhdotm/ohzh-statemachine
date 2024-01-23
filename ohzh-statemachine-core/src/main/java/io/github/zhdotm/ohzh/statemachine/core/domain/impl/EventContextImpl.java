package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class EventContextImpl<S, E> implements IEventContext<S, E> {

    @Getter
    private S stateCode;

    @Getter
    private IEvent<E> event;

    public static <S, E> EventContextImpl<S, E> getInstance() {

        return new EventContextImpl<>();
    }

    public EventContextImpl<S, E> from(@NonNull S stateCode) {
        this.stateCode = stateCode;

        return this;
    }

    public EventContextImpl<S, E> on(@NonNull IEvent<E> event) {
        this.event = event;

        return this;
    }

}
