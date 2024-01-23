package io.github.zhdotm.ohzh.statemachine.core.domain;


import java.util.Collection;

/**
 * 状态
 *
 * @author zhihao.mao
 */

public interface IState<S, E> {

    /**
     * 获取状态ID
     *
     * @return 状态ID
     */
    String getStateId();

    /**
     * 获取状态唯一编码
     *
     * @return 状态唯一编码
     */
    S getStateCode();

    /**
     * 设置状态编码
     *
     * @param stateCode 状态编码
     * @return 状态
     */
    IState<S, E> stateCode(S stateCode);

    /**
     * 添加事件编码
     *
     * @param eventCode 事件编码
     * @return 状态
     */
    IState<S, E> addEventCode(E eventCode);

    /**
     * 获取所有事件编码
     *
     * @return 事件编码
     */
    Collection<E> getEventCodes();
}
