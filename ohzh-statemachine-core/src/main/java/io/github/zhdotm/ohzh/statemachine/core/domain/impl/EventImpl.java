package io.github.zhdotm.ohzh.statemachine.core.domain.impl;

import cn.hutool.core.util.IdUtil;
import io.github.zhdotm.ohzh.statemachine.core.domain.IEvent;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhihao.mao
 */

public class EventImpl<E> implements IEvent<E> {

    @Getter
    private final Map<String, String> extraProperties = new HashMap<>();
    private String eventId;
    @Getter
    private E eventCode;
    @Getter
    private Object[] payload;

    public static <E> EventImpl<E> getInstance() {

        return new EventImpl<>();
    }

    public EventImpl<E> eventId(@NonNull String eventId) {
        this.eventId = eventId;

        return this;
    }

    public EventImpl<E> eventCode(@NonNull E eventCode) {
        this.eventCode = eventCode;

        return this;
    }

    public EventImpl<E> payload(Object... payload) {
        this.payload = payload;

        return this;
    }

    @Override
    public String getEventId() {
        if (eventId == null) {
            eventId = IdUtil.getSnowflakeNextIdStr();
        }

        return eventId;
    }

}
