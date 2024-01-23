package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine;


/**
 * @author zhihao.mao
 */

public interface IExternalTransitionBuilder<S, E, C, A> {

    IExternalTransitionBuilder<S, E, C, A> sort(Integer sort);

    IExternalTransitionFromBuilder<S, E, C, A> from(S... stateCodes);
}
