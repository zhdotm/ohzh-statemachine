package io.github.zhdotm.ohzh.statemachine.core.domain;


/**
 * 条件
 *
 * @author zhihao.mao
 */

public interface ICondition<S, E, C> {

    /**
     * 获取条件ID
     *
     * @return 条件ID
     */
    String getConditionId();

    /**
     * 获取条件编码
     *
     * @return 条件编码
     */
    C getConditionCode();

    /**
     * 是否满足条件
     *
     * @param eventContext 事件上下文
     * @return 出参
     */
    Boolean isSatisfied(IEventContext<S, E> eventContext);

}
