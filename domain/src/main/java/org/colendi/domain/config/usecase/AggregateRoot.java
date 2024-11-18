package org.colendi.domain.config.usecase;

import java.util.LinkedList;
import java.util.Queue;

public abstract class AggregateRoot {

    private final Queue<Message> events = new LinkedList<>();

    public void registerEvent(Message event) {
        events.add(event);
    }

    public void publish() {
        events.forEach(event -> {
            EventPublisher instance = EventPublisher.InstanceHolder.INSTANCE;
            if (instance != null) {
                instance.publishAsync(event);
                unRegisterEvent(event);
            }
        });
    }

    public void unRegisterEvent(Message event) {
        events.remove(event);
    }
}
