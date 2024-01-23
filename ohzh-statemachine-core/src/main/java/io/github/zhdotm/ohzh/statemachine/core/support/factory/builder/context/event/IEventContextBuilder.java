package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.event;

/**
 * @author zhihao.mao
 */

public interface IEventContextBuilder<S, E> {

    IEventContextFromBuilder<S, E> from(S stateCode);
}
