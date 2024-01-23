package io.github.zhdotm.ohzh.statemachine.core.support.factory;

import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.IEventContextBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event.impl.EventContextBuilderImpl;

/**
 * @author zhihao.mao
 */

public class EventContextFactory {

    public static <S, E> IEventContextBuilder<S, E> create() {

        return EventContextBuilderImpl.getInstance();
    }
}
