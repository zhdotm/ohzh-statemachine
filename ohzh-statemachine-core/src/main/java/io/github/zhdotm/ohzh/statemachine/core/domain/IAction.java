package io.github.zhdotm.ohzh.statemachine.core.domain;


/**
 * 动作
 *
 * @author zhihao.mao
 */

public interface IAction<A> {

    /**
     * 获取动作ID
     *
     * @return 动作ID
     */
    String getActionId();

    /**
     * 获取动作编码
     *
     * @return 动作编码
     */
    A getActionCode();

    /**
     * 执行动作
     *
     * @param args 参数
     * @return 是否执行成功
     */
    Object invoke(Object... args);

}
