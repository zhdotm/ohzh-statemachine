package io.github.zhdotm.ohzh.statemachine.core.domain;

/**
 * 事件上下文
 *
 * @author zhihao.mao
 */

public interface IEventContext<S, E> {

    /**
     * 获取状态编码
     *
     * @return 状态编码
     */
    S getStateCode();

    /**
     * 获取事件
     *
     * @return 事件
     */
    IEvent<E> getEvent();

}
