package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;

/**
 * @author zhihao.mao
 */

public interface IStateContextBuilder<S, E> {

    IStateContextOnBuilder<S, E> on(IEventContext<S, E> eventContext);
}
