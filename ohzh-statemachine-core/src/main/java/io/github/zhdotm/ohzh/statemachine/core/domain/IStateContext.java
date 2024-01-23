package io.github.zhdotm.ohzh.statemachine.core.domain;

/**
 * 状态上下文
 *
 * @author zhihao.mao
 */

public interface IStateContext<S, E> {

    /**
     * 获取状态编码
     *
     * @return 状态编码
     */
    S getStateCode();

    /**
     * 获取结果负载
     *
     * @return 结果负载
     */
    <T> T getPayload();

    /**
     * 获取事件上下文
     *
     * @return 事件上下文
     */
    IEventContext<S, E> getEventContext();
}
