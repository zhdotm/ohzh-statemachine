package io.github.zhdotm.ohzh.statemachine.core.domain;


import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.core.log.StateMachineLog;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.StateContextFactory;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateContextBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateContextOnBuilder;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.context.state.IStateContextToBuilder;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.List;

/**
 * 转换
 *
 * @author zhihao.mao
 */

public interface ITransition<S, E, C, A> {

    /**
     * 获取转换类型
     *
     * @return 转换类型
     */
    TransitionTypeEnum getType();

    /**
     * 设置转换类型
     *
     * @param type 类型
     * @return 转换
     */
    ITransition<S, E, C, A> type(TransitionTypeEnum type);

    /**
     * 获取排序号
     *
     * @return 排序号
     */
    Integer getSort();

    /**
     * 设置排序号
     *
     * @param sort 排序号
     * @return 转换
     */
    ITransition<S, E, C, A> sort(Integer sort);

    /**
     * 获取转换来源状态ID
     *
     * @return 转换来源状态ID
     */
    Collection<S> getFromStateCodes();

    /**
     * 设置初始状态
     *
     * @param stateCodes 初始状态
     * @return 转换
     */
    ITransition<S, E, C, A> from(List<S> stateCodes);

    /**
     * 获取事件ID
     *
     * @return
     */
    E getEventCode();

    /**
     * 设置事件ID
     *
     * @param eventCode 事件ID
     * @return 转换
     */
    ITransition<S, E, C, A> on(E eventCode);

    /**
     * 获取转换条件
     *
     * @return 转换条件
     */
    ICondition<S, E, C> getCondition();

    /**
     * 设置条件
     *
     * @param condition 条件
     * @return 转换
     */
    ITransition<S, E, C, A> when(ICondition<S, E, C> condition);

    /**
     * 获取动作
     *
     * @return 动作
     */
    IAction<A> getAction();

    /**
     * 设置动作
     *
     * @param action 动作
     * @return 转换
     */
    ITransition<S, E, C, A> perform(IAction<A> action);

    /**
     * 获取转换成功后的状态ID
     *
     * @return 状态ID
     */
    S getToStateCode();

    /**
     * 设置转换成功后的状态ID
     *
     * @param stateCode 状态ID
     * @return 转换
     */
    ITransition<S, E, C, A> to(S stateCode);

    @SneakyThrows
    default IStateContext<S, E> transfer(IEventContext<S, E> eventContext) {
        S stateCode = eventContext.getStateCode();
        S toStateCode = getToStateCode();
        IStateContextBuilder<S, E> stateContextBuilder = StateContextFactory.create();
        IStateContextOnBuilder<S, E> stateContextOnBuilder = stateContextBuilder.on(eventContext);
        IStateContextToBuilder<S, E> stateContextToBuilder = null;
        if (getType() == TransitionTypeEnum.EXTERNAL) {

            stateContextToBuilder = stateContextOnBuilder.to(toStateCode);
            IStateMachine.CURRENT_STATE_THREAD_LOCAL.set(String.valueOf(toStateCode));
        }

        if (getType() == TransitionTypeEnum.INTERNAL) {

            stateContextToBuilder = stateContextOnBuilder.to(stateCode);
        }

        IAction<A> action = getAction();
        A actionCode = action.getActionCode();

        StateMachineLog.info("状态机流程日志[%s, %s]: 成功匹配[%s]动作[%s]", IStateMachine.STATEMACHINE_ID_THREAD_LOCAL.get(), IStateMachine.TRACE_ID_THREAD_LOCAL.get(), getType().getDescription(), actionCode);
        Object result = action.invoke(eventContext.getEvent().getPayload());

        IStateContext<S, E> stateContext = stateContextToBuilder
                .ret(result)
                .build();

        StateMachineLog.info("状态机流程日志[%s, %s]: 执行结果[%s], 转换后状态[%s]", IStateMachine.STATEMACHINE_ID_THREAD_LOCAL.get(), IStateMachine.TRACE_ID_THREAD_LOCAL.get(), stateContext.getPayload(), stateContext.getStateCode());

        return stateContext;
    }

}
