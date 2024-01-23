package io.github.zhdotm.ohzh.statemachine.rocketmq.consumer;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.zhdotm.ohzh.statemachine.mq.consumer.IEventConsumer;
import io.github.zhdotm.ohzh.statemachine.mq.enums.StateMachineMQEnum;
import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;
import io.github.zhdotm.ohzh.statemachine.rocketmq.message.RocketMQEventContextMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.List;
import java.util.Map;

/**
 * @author zhihao.mao
 */

@Slf4j
public class RocketMQEventConsumer implements IEventConsumer, SmartInitializingSingleton, DisposableBean {

    private final Map<String, DefaultMQPushConsumer> topicConsumer = MapUtil.newConcurrentHashMap();

    private final List<String> stateMachineCodes;
    private final IEventConsumer eventConsumer;

    public RocketMQEventConsumer(List<String> stateMachineCodes, IEventConsumer eventConsumer) {
        this.stateMachineCodes = stateMachineCodes;
        this.eventConsumer = eventConsumer;
    }

    @SneakyThrows
    @Override
    public void afterSingletonsInstantiated() {
        RocketMQProperties rocketMQProperties = SpringUtil.getBean(RocketMQProperties.class);
        if (CollectionUtil.isEmpty(stateMachineCodes)) {
            return;
        }
        for (String stateMachineCode : stateMachineCodes) {
            String consumerGroup = stateMachineCode + StrUtil.UNDERLINE + SpringUtil.getApplicationName() + StateMachineMQEnum.CONSUMER_GROUP_NAME_SUFFIX.getCode();
            RocketMQProperties.Consumer mqPropertiesConsumer = rocketMQProperties.getConsumer();
            String accessKey = mqPropertiesConsumer.getAccessKey();
            String secretKey = mqPropertiesConsumer.getSecretKey();
            AclClientRPCHook rpcHook = null;
            if (StrUtil.isNotBlank(accessKey) && StrUtil.isNotBlank(secretKey)) {
                rpcHook = new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
            }
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(StateMachineMQEnum.TOPIC_NAME_SPACE.getCode(), consumerGroup, rpcHook, new AllocateMessageQueueAveragely(),
                    Boolean.TRUE, stateMachineCode);
            consumer.setNamesrvAddr(rocketMQProperties.getNameServer());
            MessageSelector messageSelector = MessageSelector.byTag("*");
            consumer.subscribe(stateMachineCode, messageSelector);
            consumer.setMessageListener(new DefaultMessageListenerConcurrently());
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.start();
            log.info("启动状态机[{}]消费者", stateMachineCode);

            topicConsumer.put(stateMachineCode, consumer);
        }
    }

    @Override
    public void destroy() throws Exception {
        topicConsumer.forEach((topic, consumer) -> consumer.shutdown());
        topicConsumer.clear();
        log.info("关闭状态机事件消费者");
    }

    @Override
    public void consume(EventContextMessage message) {
        eventConsumer.consume(message);
    }

    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    consume(RocketMQEventContextMessage.create(messageExt));
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setDelayLevelWhenNextConsume(0);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

}
