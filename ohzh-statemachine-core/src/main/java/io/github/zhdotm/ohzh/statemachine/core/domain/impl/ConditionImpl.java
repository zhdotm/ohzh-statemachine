package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.ICondition;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.enums.CharacterEnum;
import io.github.zhdotm.ohzh.statemachine.core.util.ContextUtil;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ConditionImpl<S, E, C> implements ICondition<S, E, C> {

    private Function<IEventContext<S, E>, Boolean> check;

    private String conditionId;

    @Getter
    private C conditionCode;

    public static <S, E, C> ConditionImpl<S, E, C> getInstance() {

        return new ConditionImpl<>();
    }

    public ConditionImpl<S, E, C> check(@NonNull Function<IEventContext<S, E>, Boolean> check) {
        this.check = check;

        return this;
    }

    public ConditionImpl<S, E, C> conditionId(@NonNull String conditionId) {
        this.conditionId = conditionId;

        return this;
    }

    public ConditionImpl<S, E, C> conditionCode(@NonNull C conditionCode) {
        this.conditionCode = conditionCode;

        return this;
    }

    @Override
    public String getConditionId() {
        if (conditionId == null) {
            conditionId = ContextUtil.getAppName()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + ContextUtil.getIp()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + conditionCode;
        }

        return conditionId;
    }

    @Override
    public Boolean isSatisfied(@NonNull IEventContext<S, E> eventContext) {

        return check.apply(eventContext);
    }

}
