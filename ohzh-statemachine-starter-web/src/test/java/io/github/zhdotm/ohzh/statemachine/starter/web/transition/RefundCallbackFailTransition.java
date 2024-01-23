package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;

/**
 * 回调失败（外部转换）
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.EXTERNAL,
        from = RefundStateConstant.WAIT_REFUND_CALLBACK,
        to = RefundStateConstant.REFUND_FAIL,
        on = RefundEventConstant.HAPPEN_REFUND_CALLBACK_FAIL
)
public class RefundCallbackFailTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleCallbackFail(String refundOrderId) {
        System.out.println("判断退款订单" + refundOrderId + "能否回调失败");

        return Boolean.TRUE;
    }


    @StateMachineAction
    public String callbackFail(String refundOrderId) {
        System.out.println("退款单" + refundOrderId + "回调失败");

        return "回调失败理由: 退款单" + refundOrderId + "出于xx原因回调失败";
    }

}
