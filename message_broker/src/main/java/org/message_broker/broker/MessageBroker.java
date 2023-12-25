package org.message_broker.broker;

import org.message_broker.model.Message;

import java.util.ArrayDeque;
import java.util.Queue;

public final class MessageBroker {
    private final Queue<Message> messages;
    private final int maxMessages;

    public MessageBroker(int maxMessages) {
        this.maxMessages = maxMessages;
        this.messages = new ArrayDeque<Message>(maxMessages);
    }

    public synchronized void produce(Message message) {
        try {
            while (this.messages.size() >= this.maxMessages) {
                super.wait();
            }

            this.messages.add(message);
            super.notify();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Message consume() {
        try {
            while (this.messages.isEmpty()) {
                super.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        final Message consumedMessage = this.messages.poll();
        super.notify();
        return consumedMessage;

    }
}
