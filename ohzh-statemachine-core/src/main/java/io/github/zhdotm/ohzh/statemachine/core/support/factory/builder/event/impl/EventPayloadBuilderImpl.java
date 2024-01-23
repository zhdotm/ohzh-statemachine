package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventPayloadBuilder;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class EventPayloadBuilderImpl<E> implements IEventPayloadBuilder<E> {

    private final EventImpl<E> event;

    public static <E> EventPayloadBuilderImpl<E> getInstance(EventImpl<E> event) {

        return new EventPayloadBuilderImpl<>(event);
    }

    @Override
    public IEvent<E> build() {

        return event;
    }

}
