package io.github.zhdotm.ohzh.statemachine.core;

import io.github.zhdotm.ohzh.statemachine.core.domain.IStateContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.StateMachineFactory;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IStateMachineBuilder;

import java.util.Arrays;

public class AppTest {


    public static void main(String[] args) {
        IStateMachineBuilder<StateMachineEnum, RefundStateEnum, RefundEventEnum, String, String> stateMachineBuilder = StateMachineFactory.create();
        //初始化退款订单
        stateMachineBuilder.createExternalTransition()
                .from(RefundStateEnum.STATE_REFUND_INIT)
                .on(RefundEventEnum.EVENT_INIT)
                .when("isAbleInit", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行退款订单初始化");

                    return Boolean.TRUE;
                }).perform("init", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行初始化操作");

            return "处理后" + Arrays.toString(payload);
        }).to(RefundStateEnum.STATE_WAIT_APPROVE).build();

        //待审核（添加审核意见）
        stateMachineBuilder.createInternalTransition()
                .from(RefundStateEnum.STATE_WAIT_APPROVE)
                .on(RefundEventEnum.EVENT_APPROVE_ADD_SUGGESTION)
                .when("isAbleAddSuggestion", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行添加审核意见");

                    return Boolean.TRUE;
                }).perform("addSuggestion", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行添加审核意见操作");

            return "处理后" + Arrays.toString(payload);
        }).build();


        //待审核->审核失败
        stateMachineBuilder.createExternalTransition()
                .from(RefundStateEnum.STATE_WAIT_APPROVE)
                .on(RefundEventEnum.EVENT_APPROVE_FAIL)
                .when("isAbleApproveFail", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行审核失败");

                    return Boolean.TRUE;
                }).perform("approveFail", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行审核失败操作");

            return "处理后" + Arrays.toString(payload);
        }).to(RefundStateEnum.STATE_APPROVE_FAIL).build();

        //待审核->审核通过待开始
        stateMachineBuilder.createExternalTransition()
                .from(RefundStateEnum.STATE_WAIT_APPROVE)
                .on(RefundEventEnum.EVENT_APPROVE_SUCCESS)
                .when("isAbleApproveSuccess", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行审核通过");

                    return Boolean.TRUE;
                }).perform("approveSuccess", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行审核通过操作");

            return "处理后" + Arrays.toString(payload);
        }).to(RefundStateEnum.STATE_WAIT_START).build();

        //审核通过待开始->待退款成功回调
        stateMachineBuilder.createExternalTransition()
                .from(RefundStateEnum.STATE_WAIT_START)
                .on(RefundEventEnum.EVENT_EXECUTE_REFUND)
                .when("isAbleExecuteRefund", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行退款操作");

                    return Boolean.TRUE;
                }).perform("approveSuccess", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行退款操作");

            return "处理后" + Arrays.toString(payload);
        }).to(RefundStateEnum.STATE_WAIT_REFUND_CALLBACK).build();

        //待退款成功回调->退款失败
        stateMachineBuilder.createExternalTransition()
                .from(RefundStateEnum.STATE_WAIT_REFUND_CALLBACK)
                .on(RefundEventEnum.EVENT_REFUND_CALLBACK_FAIL)
                .when("isAbleCallbackFail", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行退款回调失败操作");

                    return Boolean.TRUE;
                }).perform("callbackFail", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行退款回调失败操作");

            return "处理后" + Arrays.toString(payload);
        }).to(RefundStateEnum.STATE_REFUND_FAIL).build();

        //待退款成功回调->退款成功
        stateMachineBuilder.createExternalTransition()
                .from(RefundStateEnum.STATE_WAIT_REFUND_CALLBACK)
                .on(RefundEventEnum.EVENT_REFUND_CALLBACK_SUCCESS)
                .when("isAbleCallbackSuccess", eventContext -> {
                    System.out.println("根据事件上下文判断能否执行退款回调成功操作");

                    return Boolean.TRUE;
                }).perform("callbackSuccess", payload -> {
            System.out.println("根据携带的负载" + Arrays.toString(payload) + "执行退款回调成功操作");

            return "处理后" + Arrays.toString(payload);
        }).to(RefundStateEnum.STATE_REFUND_SUCCESS).build();

        //构建状态机
        IStateMachine<StateMachineEnum, RefundStateEnum, RefundEventEnum, String, String> stateMachine = stateMachineBuilder.build(StateMachineEnum.REFUND_STATEMACHINE);

        //导出状态图
        stateMachine.exportPlantUml();

        //发布事件
        IStateContext<RefundStateEnum, RefundEventEnum> stateContext = stateMachine.fireEvent(RefundStateEnum.STATE_REFUND_INIT, RefundEventEnum.EVENT_INIT, "xxxzzz");
        System.out.println(stateContext.getPayload() + "");

    }

    /**
     * 状态机
     */
    public enum StateMachineEnum {
        /**
         * 退款状态机
         */
        REFUND_STATEMACHINE,
        ;
    }


    /**
     * 退款状态
     */
    public enum RefundStateEnum {

        /**
         * 退款初始化
         */
        STATE_REFUND_INIT,

        /**
         * 待审核
         */
        STATE_WAIT_APPROVE,

        /**
         * 审核失败
         */
        STATE_APPROVE_FAIL,

        /**
         * 审核通过待开始
         */
        STATE_WAIT_START,

        /**
         * 待退款回调
         */
        STATE_WAIT_REFUND_CALLBACK,

        /**
         * 退款失败
         */
        STATE_REFUND_FAIL,

        /**
         * 退款回调成功
         */
        STATE_REFUND_SUCCESS,
        ;
    }


    /**
     * 退款事件
     */
    public enum RefundEventEnum {

        /**
         * 初始化退款订单事件
         */
        EVENT_INIT,

        /**
         * 添加审核意见事件
         */
        EVENT_APPROVE_ADD_SUGGESTION,

        /**
         * 审核失败事件
         */
        EVENT_APPROVE_FAIL,

        /**
         * 审核成功事件
         */
        EVENT_APPROVE_SUCCESS,

        /**
         * 执行退款事件
         */
        EVENT_EXECUTE_REFUND,

        /**
         * 退款回调成功事件
         */
        EVENT_REFUND_CALLBACK_SUCCESS,

        /**
         * 退款回调失败事件
         */
        EVENT_REFUND_CALLBACK_FAIL,
        ;
    }
}
