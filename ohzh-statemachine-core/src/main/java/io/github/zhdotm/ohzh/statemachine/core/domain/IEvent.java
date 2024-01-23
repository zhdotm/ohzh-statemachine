package io.github.zhdotm.ohzh.statemachine.core.domain;


import java.util.Map;

/**
 * 事件
 *
 * @author zhihao.mao
 */

public interface IEvent<E> {

    /**
     * 获取事件ID
     *
     * @return 事件ID
     */
    String getEventId();

    /**
     * 获取事件编码
     *
     * @return 事件编码
     */
    E getEventCode();

    /**
     * 获取参数负载
     *
     * @return 参数负载
     */
    Object[] getPayload();

    /**
     * 额外属性
     *
     * @return 额外属性
     */
    Map<String, String> getExtraProperties();
}
