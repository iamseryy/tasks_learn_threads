package org.message_broker.consumer;

import org.message_broker.broker.MessageBroker;
import org.message_broker.model.Message;

import java.util.concurrent.TimeUnit;

public final class MessageConsumingTask implements Runnable{
    private static final int SLEEP_BEFORE_CONSUME = 1;
    private static final String MESSAGE_CONSUME = "Message %s is consumed.\n";
    private final MessageBroker messageBroker;

    public MessageConsumingTask(final MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void run() {
        try {
            while (Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(SLEEP_BEFORE_CONSUME);
                final Message message = this.messageBroker.consume();
                System.out.printf(MESSAGE_CONSUME, message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
