package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event;

/**
 * @author zhihao.mao
 */

public interface IEventBuilder<E> {

    IEventPayloadBuilder<E> payload(Object... objs);
}
