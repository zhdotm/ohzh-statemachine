package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;

import java.util.UUID;

/**
 * 退款订单初始化
 *
 * @author zhihao.mao
 */

@StateMachineTransition(
        stateMachineCode = StateMachineConstant.REFUND_STATEMACHINE,
        type = TransitionTypeEnum.EXTERNAL,
        from = RefundStateConstant.REFUND_INIT,
        to = RefundStateConstant.WAIT_APPROVE,
        on = RefundEventConstant.HAPPEN_INIT
)
public class RefundInitTransition implements ISpringTransition {

    @StateMachineCondition
    public Boolean isAbleInit(String paymentOrderId) {
        System.out.println("判断支付订单" + paymentOrderId + "能否退款");

        return Boolean.TRUE;
    }

    @StateMachineAction
    public String init(String paymentOrderId) {
        String refundOrderId = UUID.randomUUID().toString();
        System.out.println("根据支付订单" + paymentOrderId + "生成退款单" + refundOrderId);

        return refundOrderId;
    }

}
