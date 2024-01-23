package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;

/**
 * 添加审核意见（内部转换）
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.INTERNAL,
        from = RefundStateConstant.WAIT_APPROVE,
        on = RefundEventConstant.HAPPEN_APPROVE_ADD_SUGGESTION
)
public class RefundApproveAddSuggestionTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleAddSuggestion(String refundOrderId) {
        System.out.println("判断退款订单" + refundOrderId + "能否添加审核意见");

        return Boolean.TRUE;
    }

    @StateMachineAction
    public String addSuggestion(String refundOrderId) {
        System.out.println("退款单" + refundOrderId + "添加审核意见");

        return "添加审核意见成功: 退款单" + refundOrderId + "添加审核意见XXXXXXXXXXXXX";
    }

}
