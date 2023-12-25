package org.message_broker.broker;

import org.message_broker.consumer.MessageConsumingTask;
import org.message_broker.model.Message;
import org.message_broker.producer.MessageProducingTask;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public final class MessageBroker {
    private static final String MESSAGE_PRODUCE = "Message %s is produced by producer '%s'. Amount of messages before producing: %d.\n";
    private static final String MESSAGE_CONSUME = "Message %s is consumed by consumer '%s'. Amount of messages before consumer: %d.\n";
    private final Queue<Message> messages;
    private final int maxMessages;


    public MessageBroker(int maxMessages) {
        this.maxMessages = maxMessages;
        this.messages = new ArrayDeque<Message>(maxMessages);
    }

    public synchronized void produce(Message message, MessageProducingTask messageProducingTask) {
        try {
            while (!this.isShouldToProduce(messageProducingTask)) {
                super.wait();
            }

            this.messages.add(message);
            System.out.printf(MESSAGE_PRODUCE, message, messageProducingTask.getName(), this.messages.size() - 1);
            super.notify();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Optional<Message> consume(final MessageConsumingTask messageConsumingTask) {
        try {
            while (!this.isShouldConsume(messageConsumingTask)) {
                super.wait();
            }

            final Message consumedMessage = this.messages.poll();
            System.out.printf(MESSAGE_CONSUME, consumedMessage, messageConsumingTask.getName(), this.messages.size() + 1);
            super.notify();
            return Optional.ofNullable(consumedMessage);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private boolean isShouldConsume(final MessageConsumingTask messageConsumingTask) {
        return !this.messages.isEmpty() && this.messages.size() >= messageConsumingTask.getMinAmountMessagesToConsume();
    }

    private boolean isShouldToProduce(final MessageProducingTask messageProducingTask) {
        return this.messages.size() < this.maxMessages && this.messages.size() <= messageProducingTask.getMaxAmountMessagesToProduce();
    }

}
