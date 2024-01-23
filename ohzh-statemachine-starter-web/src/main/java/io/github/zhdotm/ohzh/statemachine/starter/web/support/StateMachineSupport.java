package io.github.zhdotm.ohzh.statemachine.starter.web.support;

import cn.hutool.extra.spring.SpringUtil;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEventContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateContext;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventImpl;
import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;
import io.github.zhdotm.ohzh.statemachine.mq.producer.IEventProducer;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author zhihao.mao
 */

public class StateMachineSupport implements BeanFactoryPostProcessor {

    @Getter
    private static ConfigurableListableBeanFactory beanFactory;

    public static <M, S, E, C, A> IStateMachine<M, S, E, C, A> getStateMachine(M stateMachineCode) {

        return beanFactory.getBean(String.valueOf(stateMachineCode), IStateMachine.class);
    }

    public static <M, S, E, C, A> void registerStateMachine(IStateMachine<M, S, E, C, A> stateMachine) {

        beanFactory.registerSingleton(stateMachine.getStateMachineCode().toString(), stateMachine);
    }

    public static <M, S, E> IStateContext<S, E> fireEvent(M stateMachineCode, S stateCode, E eventCode, Object... payload) {
        EventContextImpl<S, E> eventContext = EventContextImpl.getInstance();
        EventImpl<E> event = EventImpl.getInstance();
        event.eventCode(eventCode)
                .payload(payload);
        eventContext.from(stateCode)
                .on(event);

        return fireEvent(stateMachineCode, eventContext);
    }

    public static <M, S, E, C, A> IStateContext<S, E> fireEvent(M stateMachineCode, IEventContext<S, E> eventContext) {
        IStateMachine<M, S, E, C, A> stateMachine = getStateMachine(stateMachineCode);

        return stateMachine.fireEvent(eventContext);
    }

    public static void fireRemoteEvent(String stateMachineCode, String stateCode, String eventCode, Object... payload) {
        EventContextImpl<String, String> eventContext = EventContextImpl.getInstance();
        EventImpl<String> event = EventImpl.getInstance();
        event.eventCode(eventCode)
                .payload(payload);
        eventContext.from(stateCode)
                .on(event);

        fireRemoteEvent(stateMachineCode, eventContext);
    }

    public static void fireRemoteEvent(String stateMachineCode, IEventContext<String, String> eventContext) {
        IEventProducer eventProducer = SpringUtil.getBean(IEventProducer.class);
        eventProducer.syncSend(EventContextMessage.create(stateMachineCode, eventContext));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        StateMachineSupport.beanFactory = configurableListableBeanFactory;
    }

}
