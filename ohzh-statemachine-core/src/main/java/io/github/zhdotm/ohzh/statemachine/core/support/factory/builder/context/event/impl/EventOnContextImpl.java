package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.IEventOnContext;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class EventOnContextImpl<S, E> implements IEventOnContext<S, E> {

    private final EventContextImpl<S, E> eventContext;

    public static <S, E> EventOnContextImpl<S, E> getInstance(EventContextImpl<S, E> eventContext) {

        return new EventOnContextImpl<>(eventContext);
    }

    @Override
    public IEventContext<S, E> build() {

        return eventContext;
    }

}
