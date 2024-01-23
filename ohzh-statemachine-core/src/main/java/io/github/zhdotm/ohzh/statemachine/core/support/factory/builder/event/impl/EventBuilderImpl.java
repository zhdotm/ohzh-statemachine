package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventCodeBuilder;

/**
 * @author zhihao.mao
 */

public class EventBuilderImpl<E> implements IEventBuilder<E> {

    public static <E> EventBuilderImpl<E> getInstance() {

        return new EventBuilderImpl<>();
    }

    @Override
    public IEventCodeBuilder<E> code(E eventCode) {
        EventImpl<E> event = EventImpl.getInstance();
        event.eventCode(eventCode);

        return EventCodeBuilderImpl.getInstance(event);
    }

}
