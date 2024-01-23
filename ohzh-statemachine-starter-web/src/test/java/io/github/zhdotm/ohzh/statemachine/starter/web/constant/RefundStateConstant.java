package io.github.zhdotm.ohzh.statemachine.starter.web.constant;

import lombok.AllArgsConstructor;

/**
 * 退款状态
 */

@AllArgsConstructor
public class RefundStateConstant {

    /**
     * 退款初始化
     */
    public static final String REFUND_INIT = "state_refund_init";

    /**
     * 待审核
     */
    public static final String WAIT_APPROVE = "state_wait_approve";

    /**
     * 审核失败
     */
    public static final String APPROVE_FAIL = "state_approve_fail";

    /**
     * 审核通过待开始
     */
    public static final String WAIT_START = "state_wait_start";

    /**
     * 待退款回调
     */
    public static final String WAIT_REFUND_CALLBACK = "state_wait_refund_callback";

    /**
     * 退款失败
     */
    public static final String REFUND_FAIL = "state_refund_fail";

    /**
     * 退款回调成功
     */
    public static final String REFUND_SUCCESS = "state_refund_success";

}
