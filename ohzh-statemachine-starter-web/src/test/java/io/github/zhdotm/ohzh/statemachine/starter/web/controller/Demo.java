package io.github.zhdotm.ohzh.statemachine.starter.web.controller;

import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.support.StateMachineSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class Demo {

    @GetMapping("/send")
    public void send() {
        StateMachineSupport.fireRemoteEvent(StateMachineConstant.REFUND_STATEMACHINE, RefundStateConstant.REFUND_INIT, RefundEventConstant.HAPPEN_INIT, "123456789");
    }

}
