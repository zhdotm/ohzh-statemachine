package io.github.zhdotm.ohzh.statemachine.starter.web.configuration;

import io.github.zhdotm.ohzh.statemachine.mq.consumer.IEventConsumer;
import io.github.zhdotm.ohzh.statemachine.mq.producer.IEventProducer;
import io.github.zhdotm.ohzh.statemachine.rocketmq.consumer.RocketMQEventConsumer;
import io.github.zhdotm.ohzh.statemachine.rocketmq.producer.RocketMQEventProducer;
import io.github.zhdotm.ohzh.statemachine.starter.web.configuration.properties.StateMachineProperties;
import io.github.zhdotm.ohzh.statemachine.starter.web.enums.StateMachineRemoteTypeEnum;
import io.github.zhdotm.ohzh.statemachine.starter.web.mq.consumer.DefaultEventConsumer;
import io.github.zhdotm.ohzh.statemachine.starter.web.processor.StateMachineProcessor;
import io.github.zhdotm.ohzh.statemachine.starter.web.runner.StateMachineRunner;
import io.github.zhdotm.ohzh.statemachine.starter.web.support.StateMachineSupport;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

/**
 * @author zhihao.mao
 */

@EnableAutoConfiguration
@Import(StateMachineProperties.class)
public class StateMachineConfiguration {

    @Bean
    public static StateMachineProcessor stateMachineProcessor() {

        return new StateMachineProcessor();
    }

    @Bean
    public static StateMachineSupport stateMachineSupport() {

        return new StateMachineSupport();
    }

    @Bean
    public StateMachineRunner stateMachineRunner() {

        return new StateMachineRunner();
    }

    @ConditionalOnProperty(prefix = "ohzh.statemachine", name = "scope", havingValue = "remote")
    @Bean
    public IEventProducer eventProducer(StateMachineProperties stateMachineProperties) {
        String remoteType = Optional.ofNullable(stateMachineProperties.getRemoteType())
                .orElse(StateMachineRemoteTypeEnum.ROECKTMQ.getCode());
        StateMachineRemoteTypeEnum stateMachineRemoteTypeEnum = Optional.ofNullable(StateMachineRemoteTypeEnum.getByCode(remoteType))
                .orElse(StateMachineRemoteTypeEnum.ROECKTMQ);
        if (stateMachineRemoteTypeEnum == StateMachineRemoteTypeEnum.ROECKTMQ) {

            return new RocketMQEventProducer();
        }
        if (stateMachineRemoteTypeEnum == StateMachineRemoteTypeEnum.KAFKA) {

            //TODO kafka的状态机插件尚未实现
        }

        return new RocketMQEventProducer();
    }

    @ConditionalOnProperty(prefix = "ohzh.statemachine", name = "scope", havingValue = "remote")
    @Bean
    public IEventConsumer eventConsumer(StateMachineProperties stateMachineProperties) {
        String remoteType = Optional.ofNullable(stateMachineProperties.getRemoteType())
                .orElse(StateMachineRemoteTypeEnum.ROECKTMQ.getCode());
        StateMachineRemoteTypeEnum stateMachineRemoteTypeEnum = Optional.ofNullable(StateMachineRemoteTypeEnum.getByCode(remoteType))
                .orElse(StateMachineRemoteTypeEnum.ROECKTMQ);
        IEventConsumer eventConsumer;
        switch (stateMachineRemoteTypeEnum) {
            case ROECKTMQ: {
                eventConsumer = new RocketMQEventConsumer(stateMachineProperties.getRemoteStatemachines(), new DefaultEventConsumer());
                break;
            }
            case KAFKA: {
                //TODO kafka的状态机插件尚未实现
            }
            default: {
                eventConsumer = new RocketMQEventConsumer(stateMachineProperties.getRemoteStatemachines(), new DefaultEventConsumer());
                break;
            }
        }

        return eventConsumer;
    }

}
