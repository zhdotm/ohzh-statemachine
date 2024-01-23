package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event;

/**
 * @author zhihao.mao
 */

public interface IEventCodeBuilder<E> {

    IEventPayloadBuilder<E> payload(Object... payload);
}
