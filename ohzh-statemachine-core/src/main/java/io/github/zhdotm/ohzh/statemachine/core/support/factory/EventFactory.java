package io.github.zhdotm.ohzh.statemachine.core.support.factory;

import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.IEventBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event.impl.EventBuilderImpl;

/**
 * @author zhihao.mao
 */

public class EventFactory {

    public static <E> IEventBuilder<E> create() {

        return EventBuilderImpl.getInstance();
    }

}
