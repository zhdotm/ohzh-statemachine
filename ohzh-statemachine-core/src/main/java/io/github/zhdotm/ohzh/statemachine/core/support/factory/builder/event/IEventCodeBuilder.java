package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;

/**
 * @author zhihao.mao
 */

public interface IEventCodeBuilder<E> {

    IEvent<E> build();
}
