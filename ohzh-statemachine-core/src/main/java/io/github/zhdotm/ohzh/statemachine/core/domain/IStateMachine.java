package io.github.zhdotm.ohzh.statemachine.core.domain;


import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventContextImpl;
import io.github.zhdotm.ohzh.statemachine.core.domain.impl.EventImpl;
import io.github.zhdotm.ohzh.statemachine.core.enums.CharacterEnum;
import io.github.zhdotm.ohzh.statemachine.core.enums.PlantUmlFileEnum;
import io.github.zhdotm.ohzh.statemachine.core.enums.PlantUmlTypeEnum;
import io.github.zhdotm.ohzh.statemachine.core.exception.StateMachineException;
import io.github.zhdotm.ohzh.statemachine.core.log.StateMachineLog;
import io.github.zhdotm.ohzh.statemachine.core.util.ExceptionUtil;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 状态机
 *
 * @author zhihao.mao
 */

public interface IStateMachine<M, S, E, C, A> {

    ThreadLocal<String> STATEMACHINE_ID_THREAD_LOCAL = new ThreadLocal<>();
    ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();
    ThreadLocal<String> CURRENT_STATE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    String getStateMachineId();

    /**
     * 获取状态机编码
     *
     * @return 状态机编码
     */
    M getStateMachineCode();

    /**
     * 设置状态机编码
     *
     * @param stateMachineCode 状态机编码
     * @return 状态机
     */
    IStateMachine<M, S, E, C, A> stateMachineCode(M stateMachineCode);

    /**
     * 获取所有状态编码
     *
     * @return 状态编码
     */
    Collection<S> getStateCodes();

    /**
     * 根据状态编码获取状态
     *
     * @param stateCode 状态编码
     * @return 状态
     */
    IState<S, E> getState(S stateCode);

    /**
     * 获取外部转换
     *
     * @param stateCode 状态编码
     * @param eventCode 事件编码
     * @return 转换
     */
    List<ITransition<S, E, C, A>> getExternalTransition(S stateCode, E eventCode);

    /**
     * 获取内部转换
     *
     * @param stateCode 状态编码
     * @param eventCode 事件编码
     * @return 转换
     */
    List<ITransition<S, E, C, A>> getInternalTransition(S stateCode, E eventCode);

    /**
     * 添加转换
     *
     * @param transitions 添加转换
     */
    void addTransitions(List<ITransition<S, E, C, A>> transitions);

    /**
     * 发布事件
     *
     * @param stateCode 状态编码
     * @param eventCode 事件编码
     * @param payload   事件负载
     * @return 状态上下文
     */
    default IStateContext<S, E> fireEvent(S stateCode, E eventCode, Object... payload) {
        EventContextImpl<S, E> eventContext = EventContextImpl.getInstance();
        EventImpl<E> event = EventImpl.getInstance();
        event.eventCode(eventCode)
                .payload(payload);
        eventContext.from(stateCode)
                .on(event);

        return fireEvent(eventContext);
    }

    /**
     * 发布事件
     *
     * @param eventContext 事件上下文
     * @return 转换成功后的状态ID
     */
    @SneakyThrows
    default IStateContext<S, E> fireEvent(IEventContext<S, E> eventContext) {
        STATEMACHINE_ID_THREAD_LOCAL.set(String.valueOf(getStateMachineCode()));
        TRACE_ID_THREAD_LOCAL.set(UUID.randomUUID().toString());
        CURRENT_STATE_THREAD_LOCAL.set(String.valueOf(eventContext.getStateCode()));
        IStateContext<S, E> stateContext = null;
        try {
            S stateCode = eventContext.getStateCode();
            IEvent<E> event = eventContext.getEvent();
            E eventCode = event.getEventCode();
            Object[] payload = event.getPayload();

            StateMachineLog.info("状态机流程日志[%s, %s]: 当前%s状态收到携带负载[%s]的事件[%s]", STATEMACHINE_ID_THREAD_LOCAL.get(), TRACE_ID_THREAD_LOCAL.get(), stateCode, Arrays.toString(payload), eventCode);

            IState<S, E> state = getState(stateCode);
            ExceptionUtil.isTrue(state != null, StateMachineException.class, "状态机[%s, %s]发布事件[%s]失败: 不存在对应的%s状态", STATEMACHINE_ID_THREAD_LOCAL.get(), TRACE_ID_THREAD_LOCAL.get(), eventCode, stateCode);

            Collection<E> eventCodes = state.getEventCodes();
            ExceptionUtil.isTrue(eventCodes.contains(eventCode), StateMachineException.class, "状态机[%s, %s]发布事件[%s]失败: 对应%s状态不存在指定事件[%s]", STATEMACHINE_ID_THREAD_LOCAL.get(), TRACE_ID_THREAD_LOCAL.get(), eventCode, stateCode, eventCode);

            List<ITransition<S, E, C, A>> satisfiedInternalTransitions = Optional.ofNullable(getInternalTransition(stateCode, eventCode))
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(transition -> transition.getCondition().isSatisfied(eventContext))
                    .sorted(Comparator.comparingInt(ITransition::getSort))
                    .collect(Collectors.toList());

            List<ITransition<S, E, C, A>> satisfiedExternalTransitions = Optional.ofNullable(getExternalTransition(stateCode, eventCode))
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(transition -> transition.getCondition().isSatisfied(eventContext))
                    .collect(Collectors.toList());

            ExceptionUtil.isTrue(satisfiedExternalTransitions.size() <= 1, StateMachineException.class, "状态机[%s]发布事件[%s]失败: %s状态指定事件[%S]对应的符合条件的外部转换超过一个", STATEMACHINE_ID_THREAD_LOCAL.get(), eventCode, stateCode, eventCode);

            for (ITransition<S, E, C, A> internalTransition : satisfiedInternalTransitions) {
                stateContext = internalTransition.transfer(eventContext);
            }

            for (ITransition<S, E, C, A> satisfiedExternalTransition : satisfiedExternalTransitions) {

                stateContext = satisfiedExternalTransition.transfer(eventContext);
            }
        } finally {
            STATEMACHINE_ID_THREAD_LOCAL.remove();
            TRACE_ID_THREAD_LOCAL.remove();
            CURRENT_STATE_THREAD_LOCAL.remove();
        }

        return stateContext;
    }

    /**
     * 导出状态机内部uml
     */
    @SneakyThrows
    default void exportPlantUml() {

        exportPlantUml("./", PlantUmlTypeEnum.STATE_DIAGRAM);
    }

    /**
     * 导出状态机内部uml
     *
     * @param filePath 输出文件路径
     */
    @SneakyThrows
    default void exportPlantUml(String filePath) {
        if (!filePath.endsWith(CharacterEnum.LEFT_SLASH.getValue())) {
            filePath = filePath + CharacterEnum.LEFT_SLASH.getValue();
        }
        exportPlantUml(filePath, PlantUmlTypeEnum.STATE_DIAGRAM);
    }

    /**
     * 导出状态机内部uml
     *
     * @param filePath         输出文件路径
     * @param plantUmlTypeEnum uml类型
     */
    @SneakyThrows
    default void exportPlantUml(String filePath, PlantUmlTypeEnum plantUmlTypeEnum) {
        String plantUml = getPlantUml(plantUmlTypeEnum);
        Files.write(Paths.get(filePath + getStateMachineCode() + PlantUmlFileEnum.FILE_SUFFIX.getValue()),
                plantUml.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 打印状态机内部结构
     *
     * @return uml
     */
    default String getPlantUml() {

        return getPlantUml(PlantUmlTypeEnum.STATE_DIAGRAM);
    }

    /**
     * 打印状态机内部结构
     *
     * @param plantUmlTypeEnum uml图类型
     * @return uml
     */
    default String getPlantUml(PlantUmlTypeEnum plantUmlTypeEnum) {
        StringBuilder plantUmlStringBuilder = new StringBuilder("@startuml \n");
        if (plantUmlTypeEnum == PlantUmlTypeEnum.SEQUENCE_DIAGRAM
                || plantUmlTypeEnum == PlantUmlTypeEnum.STATE_DIAGRAM) {
            List<S> topStateCodes = new ArrayList<>(getStateCodes());
            List<S> bottomStateCodes = new ArrayList<>();
            getStateCodes()
                    .forEach(stateCode -> {
                        IState<S, E> state = getState(stateCode);
                        state.getEventCodes()
                                .forEach(eventCode -> {
                                    Optional.ofNullable(getInternalTransition(stateCode, eventCode))
                                            .orElse(new ArrayList<>())
                                            .forEach(internalTransition -> {
                                                C conditionCode = internalTransition.getCondition().getConditionCode();
                                                A actionCode = internalTransition.getAction().getActionCode();

                                                plantUmlStringBuilder
                                                        .append(String.format("%s状态", stateCode))
                                                        .append(" --> ")
                                                        .append(String.format("%s状态", stateCode))
                                                        .append(" : ")
                                                        .append(String.format("发生%s事件, 判断%s条件, 执行%s动作", eventCode, conditionCode, actionCode))
                                                        .append(" \n");
                                            });
                                    Optional.ofNullable(getExternalTransition(stateCode, eventCode))
                                            .orElse(new ArrayList<>())
                                            .forEach(externalTransition -> {
                                                C conditionCode = externalTransition.getCondition().getConditionCode();
                                                A actionCode = externalTransition.getAction().getActionCode();
                                                S toStateCode = externalTransition.getToStateCode();
                                                topStateCodes.remove(toStateCode);
                                                if (!getStateCodes().contains(toStateCode)) {
                                                    bottomStateCodes.add(toStateCode);
                                                }
                                                plantUmlStringBuilder
                                                        .append(String.format("%s状态", stateCode))
                                                        .append(" --> ")
                                                        .append(String.format("%s状态", toStateCode))
                                                        .append(" : ")
                                                        .append(String.format("发生%s事件, 判断%s条件, 执行%s动作", eventCode, conditionCode, actionCode))
                                                        .append(" \n");
                                            });
                                });
                    });
            if (plantUmlTypeEnum == PlantUmlTypeEnum.STATE_DIAGRAM) {
                topStateCodes.forEach(stateCode -> {
                    plantUmlStringBuilder
                            .append(" [*] ")
                            .append(" --> ")
                            .append(String.format("%s状态", stateCode))
                            .append(" \n");
                });
                bottomStateCodes.forEach(stateCode -> {
                    plantUmlStringBuilder
                            .append(String.format("%s状态", stateCode))
                            .append(" --> ")
                            .append(" [*] ")
                            .append(" \n");
                });
            }
        }
        plantUmlStringBuilder.append("@enduml \n");

        return plantUmlStringBuilder.toString();
    }

}
