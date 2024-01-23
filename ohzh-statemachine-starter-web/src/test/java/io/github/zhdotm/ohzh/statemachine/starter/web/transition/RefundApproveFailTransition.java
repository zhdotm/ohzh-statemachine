package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;

/**
 * 审核失败（外部转换）
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.EXTERNAL,
        from = RefundStateConstant.WAIT_APPROVE,
        to = RefundStateConstant.APPROVE_FAIL,
        on = RefundEventConstant.HAPPEN_APPROVE_FAIL
)
public class RefundApproveFailTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleApproveFail(String refundOrderId) {
        System.out.println("判断退款订单" + refundOrderId + "能否审核失败");

        return Boolean.TRUE;
    }


    @StateMachineAction
    public String approveFail(String refundOrderId) {
        System.out.println("退款单" + refundOrderId + "审核失败");

        return "审核失败理由: 退款单" + refundOrderId + "xxxx项不符合条件";
    }
}
