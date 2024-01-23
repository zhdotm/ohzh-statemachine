package io.github.zhdotm.ohzh.statemachine.starter.web.constant;

/**
 * 退款事件
 */

public class RefundEventConstant {

    /**
     * 初始化退款订单事件
     */
    public static final String HAPPEN_INIT = "event_init";

    /**
     * 添加审核意见事件
     */
    public static final String HAPPEN_APPROVE_ADD_SUGGESTION = "event_approve_add_suggestion";

    /**
     * 审核失败事件
     */
    public static final String HAPPEN_APPROVE_FAIL = "event_approve_fail";

    /**
     * 审核成功事件
     */
    public static final String HAPPEN_APPROVE_SUCCESS = "event_approve_success";

    /**
     * 执行退款事件
     */
    public static final String HAPPEN_EXECUTE_REFUND = "event_execute_refund";

    /**
     * 退款回调成功事件
     */
    public static final String HAPPEN_REFUND_CALLBACK_SUCCESS = "event_refund_callback_success";

    /**
     * 退款回调失败事件
     */
    public static final String HAPPEN_REFUND_CALLBACK_FAIL = "event_refund_callback_fail";

}
