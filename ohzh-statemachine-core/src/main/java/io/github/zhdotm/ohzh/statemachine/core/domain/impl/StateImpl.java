package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IState;
import io.github.zhdotm.ohzh.statemachine.core.enums.CharacterEnum;
import io.github.zhdotm.ohzh.statemachine.core.util.ContextUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author zhihao.mao
 */

public class StateImpl<S, E> implements IState<S, E> {

    @Setter
    private String stateId;

    @Getter
    @Setter
    private S stateCode;

    @Getter
    @Setter
    private Collection<E> eventCodes = new HashSet<>();

    public static <S, E> StateImpl<S, E> getInstance() {

        return new StateImpl<>();
    }

    public StateImpl<S, E> stateId(@NonNull String stateId) {
        this.stateId = stateId;

        return this;
    }

    @Override
    public String getStateId() {
        if (stateId == null) {
            stateId = ContextUtil.getAppName()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + ContextUtil.getIp()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + stateCode;
        }

        return stateId;
    }

    @Override
    public StateImpl<S, E> stateCode(@NonNull S stateCode) {
        this.stateCode = stateCode;

        return this;
    }

    @Override
    public StateImpl<S, E> addEventCode(@NonNull E eventCode) {
        eventCodes.add(eventCode);

        return this;
    }

}
