package io.github.zhdotm.ohzh.statemachine.starter.web.processor;

import io.github.zhdotm.ohzh.statemachine.core.enums.CharacterEnum;
import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import io.github.zhdotm.ohzh.statemachine.core.exception.StateMachineException;
import io.github.zhdotm.ohzh.statemachine.core.util.ExceptionUtil;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.transition.ISpringTransition;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

/**
 * @author zhihao.mao
 */

public class StateMachineProcessor implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        StateMachineTransition stateMachineTransition = bean
                .getClass()
                .getDeclaredAnnotation(StateMachineTransition.class);

        if (stateMachineTransition != null) {

            if (stateMachineTransition.type() == TransitionTypeEnum.EXTERNAL) {

                ExceptionUtil.isTrue(!CharacterEnum.EMPTY.getValue().equalsIgnoreCase(stateMachineTransition.to()), StateMachineException.class, "构建Bean[%s]失败: 外部转换必须指定转换后状态", beanName);
            }

            ExceptionUtil.isTrue(bean instanceof ISpringTransition, StateMachineException.class, "构建Bean[%s]失败: 携带@StateMachineComponent注解的Bean必须实现ISpringTransition接口", beanName);
        }

        return bean;
    }

}
