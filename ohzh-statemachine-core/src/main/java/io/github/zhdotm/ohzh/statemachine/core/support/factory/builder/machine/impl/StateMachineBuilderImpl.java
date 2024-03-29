package io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.impl;


import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.StateMachineImpl;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.TransitionImpl;
import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IExternalTransitionBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IInternalTransitionBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IStateMachineBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhihao.mao
 */

public class StateMachineBuilderImpl<M, S, E, C, A> implements IStateMachineBuilder<M, S, E, C, A> {

    private final StateMachineImpl<M, S, E, C, A> stateMachine = StateMachineImpl.getInstance();

    private final List<ITransition<S, E, C, A>> transitions = new ArrayList<>();

    public static <M, S, E, C, A> StateMachineBuilderImpl<M, S, E, C, A> getInstance() {

        return new StateMachineBuilderImpl<>();
    }

    @Override
    public void transitions(List<ITransition<S, E, C, A>> transitions) {
        this.transitions.clear();
        this.transitions.addAll(transitions);
    }

    @Override
    public IExternalTransitionBuilder<S, E, C, A> createExternalTransition() {
        TransitionImpl<S, E, C, A> transition = TransitionImpl.getInstance();
        transition.type(TransitionTypeEnum.EXTERNAL);
        transitions.add(transition);

        return ExternalTransitionBuilderImpl.getInstance(transition);
    }

    @Override
    public IInternalTransitionBuilder<S, E, C, A> createInternalTransition() {
        TransitionImpl<S, E, C, A> transition = TransitionImpl.getInstance();
        transition.type(TransitionTypeEnum.INTERNAL);
        transitions.add(transition);

        return InternalTransitionBuilderImpl.getInstance(transition);
    }

    @Override
    public IStateMachine<M, S, E, C, A> build(M stateMachineCode) {
        stateMachine.stateMachineCode(stateMachineCode);
        stateMachine.addTransitions(transitions);

        return stateMachine;
    }

}
