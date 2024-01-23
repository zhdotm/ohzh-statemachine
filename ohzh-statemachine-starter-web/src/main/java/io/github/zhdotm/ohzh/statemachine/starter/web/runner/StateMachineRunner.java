package io.github.zhdotm.ohzh.statemachine.starter.web.runner;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.zhdotm.ohzh.statemachine.core.domain.IStateMachine;
import io.github.zhdotm.ohzh.statemachine.core.domain.ITransition;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.StateMachineFactory;
import io.github.zhdotm.ohzh.statemachine.core.support.factory.builder.machine.IStateMachineBuilder;
import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.StateMachineTransition;
import io.github.zhdotm.ohzh.statemachine.starter.web.configuration.properties.StateMachineProperties;
import io.github.zhdotm.ohzh.statemachine.starter.web.enums.StateMachineScopeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.support.StateMachineSupport;
import io.github.zhdotm.ohzh.statemachine.starter.web.transition.ISpringTransition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhihao.mao
 */

@Slf4j
public class StateMachineRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StateMachineProperties stateMachineProperties = SpringUtil.getBean(StateMachineProperties.class);
        String scope = stateMachineProperties.getScope();
        List<String> stateMachineCodes = Optional.ofNullable(stateMachineProperties.getLocalStatemachines())
                .orElse(ListUtil.list(Boolean.FALSE));
        if (StateMachineScopeEnum.REMOTE.getCode().equalsIgnoreCase(scope)) {
            List<String> remoteStateMachineCodes = Optional.ofNullable(stateMachineProperties.getRemoteStatemachines())
                    .orElse(ListUtil.list(Boolean.FALSE));
            stateMachineCodes.addAll(remoteStateMachineCodes);
        }
        stateMachineCodes = stateMachineCodes.stream()
                .distinct()
                .collect(Collectors.toList());
        Map<String, List<ITransition<String, String, String, String>>> stateMachineCodeTransitionsMap = getStateMachineCodeTransitionsMap();

        for (String stateMachineCode : stateMachineCodeTransitionsMap.keySet()) {
            if (!stateMachineCodes.contains(stateMachineCode)) {
                continue;
            }

            List<ITransition<String, String, String, String>> transitions = stateMachineCodeTransitionsMap.get(stateMachineCode);
            IStateMachineBuilder<String, String, String, String, String> stateMachineBuilder = StateMachineFactory.create();
            stateMachineBuilder.transitions(transitions);
            IStateMachine<String, String, String, String, String> stateMachine = stateMachineBuilder.build(stateMachineCode);

            StateMachineSupport.registerStateMachine(stateMachine);
        }
    }

    private Map<String, List<ITransition<String, String, String, String>>> getStateMachineCodeTransitionsMap() {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineCodeTransitionsMap = new HashMap<>();

        ConfigurableListableBeanFactory beanFactory = StateMachineSupport.getBeanFactory();
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(StateMachineTransition.class);
        for (Object bean : beansWithAnnotation.values()) {
            ISpringTransition springTransition = (ISpringTransition) bean;
            String stateMachineCode = springTransition.getStateMachineCode();
            List<ITransition<String, String, String, String>> transitions = stateMachineCodeTransitionsMap.getOrDefault(stateMachineCode, new ArrayList<>());
            ITransition<String, String, String, String> transition = springTransition.getTransition();
            transitions.add(transition);
            stateMachineCodeTransitionsMap.put(stateMachineCode, transitions);
        }

        return stateMachineCodeTransitionsMap;
    }

}
