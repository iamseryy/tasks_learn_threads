package org.message_broker.producer;

import org.message_broker.broker.MessageBroker;
import org.message_broker.model.Message;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public final class MessageProducingTask implements Runnable {
    private static final int SLEEP_BEFORE_PRODUCING = 1;
    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;
    private final int maxAmountMessagesToProduce;
    private final String name;

    public MessageProducingTask (final MessageBroker messageBroker, final MessageFactory messageFactory,
                                 final int maxAmountMessagesToProduce, final String name) {
        this.messageBroker = messageBroker;
        this.messageFactory = messageFactory;
        this.maxAmountMessagesToProduce = maxAmountMessagesToProduce;
        this.name = name;

    }

    public int getMaxAmountMessagesToProduce() {
        return maxAmountMessagesToProduce;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
                while (!Thread.currentThread().isInterrupted()) {
                    final Message message = this.messageFactory.create();
                    TimeUnit.SECONDS.sleep(SLEEP_BEFORE_PRODUCING);
                    this.messageBroker.produce(message, this);
                }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
