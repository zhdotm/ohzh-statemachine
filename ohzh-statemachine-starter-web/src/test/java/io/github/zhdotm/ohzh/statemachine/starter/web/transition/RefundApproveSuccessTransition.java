package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;

/**
 * 审核通过（外部转换）
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.EXTERNAL,
        from = RefundStateConstant.WAIT_APPROVE,
        to = RefundStateConstant.WAIT_START,
        on = RefundEventConstant.HAPPEN_APPROVE_SUCCESS
)
public class RefundApproveSuccessTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleApproveSuccess(String refundOrderId) {
        System.out.println("判断退款订单" + refundOrderId + "能否审核通过");

        return Boolean.TRUE;
    }

    @StateMachineAction
    public String approveSuccess(String refundOrderId) {
        System.out.println("退款单" + refundOrderId + "审核通过");

        return "审核通过: 退款单" + refundOrderId + "审核通过";
    }

}
