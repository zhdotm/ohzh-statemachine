package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;


/**
 * 执行退款（外部转换）
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.EXTERNAL,
        from = RefundStateConstant.WAIT_START,
        to = RefundStateConstant.WAIT_REFUND_CALLBACK,
        on = RefundEventConstant.HAPPEN_EXECUTE_REFUND
)
public class RefundExecuteRefundTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleExecuteRefund(String refundOrderId) {
        System.out.println("判断退款订单" + refundOrderId + "能否执行退款操作");

        return Boolean.TRUE;
    }


    @StateMachineAction
    public String executeRefund(String refundOrderId) {
        System.out.println("退款单" + refundOrderId + "执行退款操作");

        return "成功执行退款操作: 退款单" + refundOrderId + "成功执行退款操作";
    }

}
