package io.github.zhdotm.ohzh.statemachine.starter.web.transition;

import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.ActionImpl;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.ConditionImpl;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.TransitionImpl;
import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.core.exception.StateMachineException;
import io.github.zhdotm.ohzh.statemachine.core.util.ExceptionUtil;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineCondition;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface ISpringTransition {

    default String getCurrentState() {

        return IStateMachine.CURRENT_STATE_THREAD_LOCAL.get();
    }

    default ITransition<String, String, String, String> getTransition() {
        Object obj = this;
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        StateMachineTransition stateMachineTransition = clazz.getDeclaredAnnotation(StateMachineTransition.class);
        ExceptionUtil.isTrue(stateMachineTransition != null, StateMachineException.class, "获取转换失败: 类[%s]不存在@StateMachineComponent", clazzName);
        Method conditionMethod = getConditionMethod();
        String conditionMethodName = conditionMethod.getName();
        Class<?>[] conditionMethodParameterTypes = conditionMethod.getParameterTypes();
        Method actionMethod = getActionMethod();
        String actionMethodName = getActionMethod().getName();
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        ExceptionUtil.isTrue(matchMethodParameterTypes(conditionMethodParameterTypes, actionMethodParameterTypes), StateMachineException.class, "获取转换失败: 类[%s]的条件方法[%s]的参数和动作方法[%s]的参数不匹配", clazzName, conditionMethodName, actionMethodName);

        TransitionImpl<String, String, String, String> transition = TransitionImpl.getInstance();
        ConditionImpl<String, String, String> condition = ConditionImpl.getInstance();
        condition.conditionCode(getConditionCode())
                .check(new Function<IEventContext<String, String>, Boolean>() {
                    @SneakyThrows
                    @Override
                    public Boolean apply(IEventContext<String, String> eventContext) {
                        IEvent<String> event = eventContext.getEvent();
                        Object[] args = convertPayloadToArgs(event.getPayload(), conditionMethod, clazzName);

                        return (Boolean) conditionMethod.invoke(obj, args);
                    }
                });
        ActionImpl<String> action = ActionImpl.getInstance();
        action.actionCode(getActionCode())
                .execute(new Function<Object[], Object>() {
                    @SneakyThrows
                    @Override
                    public Object apply(Object[] args) {
                        args = convertPayloadToArgs(args, actionMethod, clazzName);

                        return actionMethod.invoke(obj, args);
                    }
                });
        transition.type(stateMachineTransition.type())
                .sort(stateMachineTransition.sort())
                .from(Arrays.asList(stateMachineTransition.from()))
                .on(stateMachineTransition.on())
                .when(condition)
                .perform(action);
        if (stateMachineTransition.type() == TransitionTypeEnum.EXTERNAL) {
            transition.to(stateMachineTransition.to());
        }

        return transition;
    }

    default Boolean matchMethodParameterTypes(Class<?>[] conditionMethodParameterTypes, Class<?>[] actionMethodParameterTypes) {
        if (conditionMethodParameterTypes.length != actionMethodParameterTypes.length) {

            return Boolean.FALSE;
        }

        for (int i = 0; i < conditionMethodParameterTypes.length; i++) {
            Class<?> conditionMethodParameterType = conditionMethodParameterTypes[i];
            Class<?> actionMethodParameterType = actionMethodParameterTypes[i];
            if (conditionMethodParameterType != actionMethodParameterType) {

                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    default StateMachineTransition getTransitionAnnotation() {
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        StateMachineTransition stateMachineTransition = clazz.getDeclaredAnnotation(StateMachineTransition.class);
        ExceptionUtil.isTrue(stateMachineTransition != null, StateMachineException.class, "获取stateMachineCode失败: 类[%s]不存在@StateMachineComponent", clazzName);

        return stateMachineTransition;
    }

    default String getStateMachineCode() {

        return getTransitionAnnotation().stateMachineCode();
    }

    default String getConditionCode() {
        Method conditionMethod = getConditionMethod();
        StateMachineCondition stateMachineCondition = conditionMethod.getDeclaredAnnotation(StateMachineCondition.class);
        String conditionCode = stateMachineCondition.conditionCode();

        return conditionCode.length() == 0 ? conditionMethod.getName() : conditionCode;
    }

    default Method getConditionMethod() {
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        Method method = getStateMachineComponentMethod(StateMachineCondition.class);
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        ExceptionUtil.isTrue(returnType == Boolean.class, StateMachineException.class, "获取条件方法失败: 类[%s]的条件方法[%s]的返回值不为Boolean类型", clazzName, methodName);

        return method;
    }

    default String getActionCode() {
        Method actionMethod = getActionMethod();
        StateMachineAction stateMachineAction = actionMethod.getDeclaredAnnotation(StateMachineAction.class);
        String actionCode = stateMachineAction.actionCode();

        return actionCode.length() == 0 ? actionMethod.getName() : actionCode;
    }

    default Method getActionMethod() {

        return getStateMachineComponentMethod(StateMachineAction.class);
    }

    default Method getStateMachineComponentMethod(Class<? extends Annotation> annotationClazz) {
        ExceptionUtil.isTrue(annotationClazz == StateMachineCondition.class || annotationClazz == StateMachineAction.class, StateMachineException.class, "获取状态机组件方法失败: 只支持@StateMachineCondition、@StateMachineAction");
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        StateMachineTransition stateMachineTransition = clazz.getDeclaredAnnotation(StateMachineTransition.class);
        if (stateMachineTransition == null) {

            return null;
        }

        Method[] methods = clazz.getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            Annotation annotation = method.getDeclaredAnnotation(annotationClazz);
            if (annotation == null) {

                continue;
            }
            ExceptionUtil.isTrue(targetMethod == null, StateMachineException.class, "获取状态机组件方法失败: 类[%s]存在多个指定注解[%s]的方法", clazzName, annotationClazz.getName());
            targetMethod = method;
        }

        ExceptionUtil.isTrue(targetMethod != null, StateMachineException.class, "获取状态机组件方法失败: 类[%s]不存在指定注解[%s]的方法", clazzName, annotationClazz.getName());

        return targetMethod;
    }

    default Object[] convertPayloadToArgs(Object[] payload, Method method, String clazzName) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        payload = Optional
                .ofNullable(payload)
                .orElse(new Object[0]);
        int payloadLength = payload.length;
        int parameterTypesLength = parameterTypes.length;
        ExceptionUtil.isTrue(payloadLength <= parameterTypesLength, StateMachineException.class, "事件负载[%s]转换为类[%s]的方法[%s]的执行参数失败: 事件负载参数个数大于执行参数个数", Arrays.toString(payload), clazzName, methodName);
        Object[] args = new Object[parameterTypesLength];
        for (int i = 0; i < args.length; i++) {
            if (i < payloadLength) {
                ExceptionUtil.isTrue(payload[i].getClass() == parameterTypes[i], StateMachineException.class, "事件负载[%s]转换为类[%s]的方法[%s]的执行参数失败: 参数类型不匹配", Arrays.toString(payload), clazzName, methodName);
                args[i] = payload[i];
            }
        }

        return args;
    }

}
