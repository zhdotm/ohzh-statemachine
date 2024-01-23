package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.event;

/**
 * @author zhihao.mao
 */

public interface IEventPayloadBuilder<E> {

    IEventCodeBuilder<E> id(E eventCode);
}
