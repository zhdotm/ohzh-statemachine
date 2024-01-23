package io.github.zhdotm.ohzh.statemachine.rocketmq.producer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import io.github.zhdotm.ohzh.statemachine.mq.enums.StateMachineMQEnum;
import io.github.zhdotm.ohzh.statemachine.mq.message.EventContextMessage;
import io.github.zhdotm.ohzh.statemachine.mq.producer.EventSendResult;
import io.github.zhdotm.ohzh.statemachine.mq.producer.IEventProducer;
import io.github.zhdotm.ohzh.statemachine.mq.producer.IEventSendCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhihao.mao
 */

@Slf4j
public class RocketMQEventProducer implements SmartInitializingSingleton, DisposableBean, IEventProducer {

    private MQProducer producer;

    @SneakyThrows
    @Override
    public EventSendResult syncSend(EventContextMessage message) {
        try {
            SendResult sendResult = producer.send(transfer(message));
            log.info("同步发送结果: {}", sendResult);

            return RocketMQEventSendResult.create(sendResult);
        } catch (Exception e) {
            log.error("同步发送消息异常: ", e);

            throw e;
        }
    }

    @SneakyThrows
    @Override
    public void asyncSend(EventContextMessage message, IEventSendCallback eventSendCallback) {
        try {
            producer.send(transfer(message), new RocketMQEventSendCallback(eventSendCallback));

        } catch (Exception e) {
            log.error("异步发送消息异常: ", e);

            throw e;
        }
    }

    /**
     * 转换为 RocketMQ 消息
     *
     * @param message 消息
     * @return mq消息
     */
    private Message transfer(EventContextMessage message) {
        Map<String, String> extraProperties = message.getExtraProperties();

        return new Message(message.getTopic(), extraProperties.get(StateMachineMQEnum.TAGS.getCode()), extraProperties.get(StateMachineMQEnum.KEYS.getCode()), JSONUtil.toJsonStr(message).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void destroy() throws Exception {
        if (ObjectUtil.isNotEmpty(producer)) {
            producer.shutdown();
        }
        log.info("关闭状态机事件生产者");
    }

    @SneakyThrows
    @Override
    public void afterSingletonsInstantiated() {
        RocketMQProperties rocketMQProperties = SpringUtil.getBean(RocketMQProperties.class);
        RocketMQProperties.Producer producerConfig = rocketMQProperties.getProducer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = Optional.ofNullable(producerConfig.getGroup())
                .orElse(SpringUtil.getApplicationName() + StateMachineMQEnum.PRODUCER_GROUP_NAME_SUFFIX.getCode());

        DefaultMQProducer producer;
        String accessKey = rocketMQProperties.getProducer().getAccessKey();
        String secretKey = rocketMQProperties.getProducer().getSecretKey();
        AclClientRPCHook rpcHook = null;
        if (StrUtil.isNotBlank(accessKey) && StrUtil.isNotBlank(secretKey)) {
            rpcHook = new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
        }
        producer = new DefaultMQProducer(groupName, rpcHook, Boolean.TRUE, rocketMQProperties.getProducer().getCustomizedTraceTopic());

        String accessChannel = rocketMQProperties.getAccessChannel();
        if (StrUtil.isNotBlank(accessChannel)) {
            producer.setAccessChannel(AccessChannel.valueOf(accessChannel.toUpperCase()));
        }
        producer.setNamespace(StateMachineMQEnum.TOPIC_NAME_SPACE.getCode());
        producer.setNamesrvAddr(nameServer);
        producer.setSendMsgTimeout(producerConfig.getSendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMessageBodyThreshold());

        this.producer = producer;
        producer.start();
        log.info("启动状态机事件生产者");
    }

}
