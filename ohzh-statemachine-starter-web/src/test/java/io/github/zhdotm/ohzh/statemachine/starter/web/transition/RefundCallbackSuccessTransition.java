package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;

/**
 * 回调成功
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.EXTERNAL,
        from = RefundStateConstant.WAIT_REFUND_CALLBACK,
        to = RefundStateConstant.REFUND_SUCCESS,
        on = RefundEventConstant.HAPPEN_REFUND_CALLBACK_SUCCESS
)
public class RefundCallbackSuccessTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleCallbackSuccess(String refundOrderId) {
        System.out.println("判断退款订单" + refundOrderId + "能否回调成功");

        return Boolean.TRUE;
    }


    @StateMachineAction
    public String callbackSuccess(String refundOrderId) {
        System.out.println("退款单" + refundOrderId + "回调成功");

        return "回调成功理由: 退款单" + refundOrderId + "成功退款";
    }

}
