package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IState;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.enums.CharacterEnum;
import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.core.util.ContextUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

/**
 * @author zhihao.mao
 */

public class StateMachineImpl<M, S, E, C, A> implements IStateMachine<M, S, E, C, A> {

    private final Map<S, IState<S, E>> stateMap = new HashMap<>();
    private final Map<String, List<ITransition<S, E, C, A>>> externalTransitionMap = new HashMap<>();
    private final Map<String, List<ITransition<S, E, C, A>>> internalTransitionMap = new HashMap<>();

    @Setter
    private String stateMachineId;

    @Getter
    @Setter
    private M stateMachineCode;

    public static <M, S, E, C, A> StateMachineImpl<M, S, E, C, A> getInstance() {

        return new StateMachineImpl<>();
    }

    public IStateMachine<M, S, E, C, A> stateMachineId(@NonNull String stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    @Override
    public String getStateMachineId() {
        if (stateMachineId == null) {
            stateMachineId = ContextUtil.getAppName()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + ContextUtil.getIp()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + stateMachineCode;
        }

        return stateMachineId;
    }

    @Override
    public IStateMachine<M, S, E, C, A> stateMachineCode(@NonNull M stateMachineCode) {
        this.stateMachineCode = stateMachineCode;

        return this;
    }

    @Override
    public Collection<S> getStateCodes() {

        return stateMap.keySet();
    }

    @Override
    public IState<S, E> getState(@NonNull S stateCode) {

        return stateMap.get(stateCode);
    }

    @Override
    public List<ITransition<S, E, C, A>> getExternalTransition(@NonNull S stateCode, @NonNull E eventCode) {

        return externalTransitionMap.get(stateCode + CharacterEnum.HASH_TAG.getValue() + eventCode);
    }

    @Override
    public List<ITransition<S, E, C, A>> getInternalTransition(@NonNull S stateCode, @NonNull E eventCode) {

        return internalTransitionMap.get(stateCode + CharacterEnum.HASH_TAG.getValue() + eventCode);
    }

    @Override
    public void addTransitions(List<ITransition<S, E, C, A>> transitions) {
        for (ITransition<S, E, C, A> transition : transitions) {

            addTransition(transition);
        }
    }

    @SneakyThrows
    private void addTransition(ITransition<S, E, C, A> transition) {
        TransitionTypeEnum type = transition.getType();
        Collection<S> fromStateCodes = transition.getFromStateCodes();
        E eventCode = transition.getEventCode();
        for (S fromStateCode : fromStateCodes) {
            IState<S, E> state = getState(fromStateCode);
            if (state == null) {
                state = StateImpl.getInstance();
            }
            state.stateCode(fromStateCode)
                    .addEventCode(eventCode);
            stateMap.put(fromStateCode, state);

            Map<String, List<ITransition<S, E, C, A>>> transitionMap = null;
            if (type == TransitionTypeEnum.EXTERNAL) {

                transitionMap = externalTransitionMap;
            }
            if (type == TransitionTypeEnum.INTERNAL) {

                transitionMap = internalTransitionMap;
            }

            List<ITransition<S, E, C, A>> transitions = transitionMap.getOrDefault(fromStateCode + CharacterEnum.HASH_TAG.getValue() + eventCode, new ArrayList<>());
            transitions.add(transition);
            transitionMap.put(fromStateCode + CharacterEnum.HASH_TAG.getValue() + eventCode, transitions);
        }
    }

}
