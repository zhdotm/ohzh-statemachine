package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;

/**
 * @author zhihao.mao
 */
public interface IEventContextFromBuilder<S, E> {

    IEventOnContext<S, E> on(IEvent<E> event);
}
