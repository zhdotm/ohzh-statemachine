package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventImpl;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventPayloadBuilder;

/**
 * @author zhihao.mao
 */

public class EventBuilderImpl<E> implements IEventBuilder<E> {

    public static <E> EventBuilderImpl<E> getInstance() {

        return new EventBuilderImpl<>();
    }

    @Override
    public IEventPayloadBuilder<E> payload(Object... objs) {
        EventImpl<E> event = EventImpl.getInstance();
        event.payload(objs);

        return EventPayloadBuilderImpl.getInstance(event);
    }

}
