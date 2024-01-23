package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state;

import io.github.zhdotm.ohzh.statemachine.core.domain.IStateContext;

/**
 * @author zhihao.mao
 */

public interface IStateRetContext<S, E> {

    IStateContext<S, E> build();
}
