package org.message_broker.consumer;

import org.message_broker.broker.MessageBroker;
import org.message_broker.model.Message;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class MessageConsumingTask implements Runnable{
    private static final int SLEEP_BEFORE_CONSUME = 1;
    private final int minAmountMessagesToConsume;

    private final MessageBroker messageBroker;
    private final String name;

    public MessageConsumingTask(final MessageBroker messageBroker, final int minAmountMessagesToConsume, final String name) {
        this.messageBroker = messageBroker;
        this.minAmountMessagesToConsume = minAmountMessagesToConsume;
        this.name = name;
    }

    public int getMinAmountMessagesToConsume() {
        return minAmountMessagesToConsume;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(SLEEP_BEFORE_CONSUME);
                final Optional<Message> optMessage = this.messageBroker.consume(this);
                optMessage.orElseThrow(MessageConsumingException::new);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
