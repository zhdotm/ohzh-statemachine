package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.IEventContextFromBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.IEventOnContext;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class EventContextFromBuilderImpl<S, E> implements IEventContextFromBuilder<S, E> {

    private final EventContextImpl<S, E> eventContext;

    public static <S, E> EventContextFromBuilderImpl<S, E> getInstance(EventContextImpl<S, E> eventContext) {

        return new EventContextFromBuilderImpl<>(eventContext);
    }

    @Override
    public IEventOnContext<S, E> on(IEvent<E> event) {
        eventContext.on(event);

        return EventOnContextImpl.getInstance(eventContext);
    }

}
