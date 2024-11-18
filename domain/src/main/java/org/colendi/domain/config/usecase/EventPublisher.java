package org.colendi.domain.config.usecase;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

@DomainComponent
public class EventPublisher {

    private final ApplicationContextPort applicationContext;

    public EventPublisher(ApplicationContextPort applicationContext) {
        this.applicationContext = applicationContext;
        InstanceHolder.INSTANCE = this;
    }

    public void publish(Message message) {
        applicationContext.getBeansWithAnnotation(MessageDriven.class)
                .values()
                .forEach(event -> {
                    MessageDriven messageDriven = event.getClass().getAnnotation(MessageDriven.class);
                    String method = messageDriven.method();
                    Class<?> messageClass = messageDriven.message();
                    if (messageClass.isInstance(message)) {
                        try {
                            event.getClass().getMethod(method, messageClass).invoke(event, message);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }

                });
    }

    public void publishAsync(Message message) {
        CompletableFuture.runAsync(() -> publish(message)).exceptionally(e -> {
            throw new RuntimeException(e);
        });
    }

    public static class InstanceHolder {
        public static EventPublisher INSTANCE;

        public static void setINSTANCE(EventPublisher INSTANCE) {
            InstanceHolder.INSTANCE = INSTANCE;
        }

        private InstanceHolder() {
        }
    }
}
