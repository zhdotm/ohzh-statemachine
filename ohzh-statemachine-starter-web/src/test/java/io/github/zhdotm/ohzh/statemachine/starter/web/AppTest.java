package io.github.zhdotm.ohzh.statemachine.starter.web;


import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundEventConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.RefundStateConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.constant.StateMachineConstant;
import io.github.zhdotm.ohzh.statemachine.starter.web.support.StateMachineSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest {

    @Test
    public void fireEvent() {
        StateMachineSupport.fireEvent(StateMachineConstant.REFUND_STATEMACHINE, RefundStateConstant.REFUND_INIT, RefundEventConstant.HAPPEN_INIT, "123456789");
    }

    @Test
    public void fireRemoteEvent() {
        StateMachineSupport.fireRemoteEvent(StateMachineConstant.REFUND_STATEMACHINE, RefundStateConstant.REFUND_INIT, RefundEventConstant.HAPPEN_INIT, "123456789");
    }

    @Test
    public void print() {
        System.out.println(StateMachineSupport
                .getStateMachine(StateMachineConstant.REFUND_STATEMACHINE)
                .getPlantUml());
    }

    @Test
    public void export() {
        StateMachineSupport
                .getStateMachine(StateMachineConstant.REFUND_STATEMACHINE)
                .exportPlantUml();
    }

}
