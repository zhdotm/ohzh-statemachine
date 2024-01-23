package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventCodeBuilder;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class EventCodeBuilderImpl<E> implements IEventCodeBuilder<E> {

    private final EventImpl<E> event;

    public static <E> EventCodeBuilderImpl<E> getInstance(EventImpl<E> event) {

        return new EventCodeBuilderImpl<>(event);
    }

    @Override
    public IEvent<E> build() {

        return event;
    }

}
