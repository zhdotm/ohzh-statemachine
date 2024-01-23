package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IAction;
import io.github.zhdotm.ohzh.statemachine.core.domain.ICondition;
import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhihao.mao
 */

public class TransitionImpl<S, E, C, A> implements ITransition<S, E, C, A> {

    @Getter
    private final Collection<S> fromStateCodes = new HashSet<>();

    @Getter
    private TransitionTypeEnum type;

    @Getter
    private Integer sort = Integer.MAX_VALUE;

    @Getter
    private S toStateCode;

    @Getter
    private E eventCode;

    @Getter
    private ICondition<S, E, C> condition;

    @Getter
    private IAction<A> action;

    public static <S, E, C, A> TransitionImpl<S, E, C, A> getInstance() {

        return new TransitionImpl<>();
    }

    @Override
    public ITransition<S, E, C, A> type(@NonNull TransitionTypeEnum type) {
        this.type = type;

        return this;
    }

    @Override
    public ITransition<S, E, C, A> sort(@NonNull Integer sort) {
        this.sort = sort;

        return this;
    }

    @Override
    public TransitionImpl<S, E, C, A> from(@NonNull List<S> stateCodes) {
        fromStateCodes.addAll(stateCodes);

        return this;
    }

    @Override
    public ITransition<S, E, C, A> on(@NonNull E eventCode) {
        this.eventCode = eventCode;

        return this;
    }

    @Override
    public ITransition<S, E, C, A> to(@NonNull S stateCode) {
        toStateCode = stateCode;

        return this;
    }

    @Override
    public TransitionImpl<S, E, C, A> when(@NonNull ICondition<S, E, C> condition) {
        this.condition = condition;

        return this;
    }

    @Override
    public TransitionImpl<S, E, C, A> perform(@NonNull IAction<A> action) {
        this.action = action;

        return this;
    }
}
