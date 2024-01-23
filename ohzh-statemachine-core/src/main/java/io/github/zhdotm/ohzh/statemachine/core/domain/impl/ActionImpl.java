package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import io.github.zhdotm.ohzh.statemachine.core.domain.IAction;
import io.github.zhdotm.ohzh.statemachine.core.enums.CharacterEnum;
import io.github.zhdotm.ohzh.statemachine.core.util.ContextUtil;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ActionImpl<A> implements IAction<A> {

    private String actionId;

    @Getter
    private A actionCode;

    private Function<Object[], Object> execute;

    public static <A> ActionImpl<A> getInstance() {

        return new ActionImpl<>();
    }

    public ActionImpl<A> actionId(@NonNull String actionId) {
        this.actionId = actionId;

        return this;
    }

    public ActionImpl<A> actionCode(@NonNull A actionCode) {
        this.actionCode = actionCode;

        return this;
    }

    public ActionImpl<A> execute(@NonNull Function<Object[], Object> execute) {
        this.execute = execute;

        return this;
    }

    @Override
    public String getActionId() {
        if (actionId == null) {
            actionId = ContextUtil.getAppName()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + ContextUtil.getIp()
                    + CharacterEnum.PERCENT_SIGN.getValue()
                    + actionCode;
        }

        return actionId;
    }

    @Override
    public Object invoke(Object... args) {


        return execute.apply(args);
    }

}
