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

    public void produce(Message message) {
        this.messages.add(message);
    }

    public Message consume() {
        return this.messages.poll();
    }
}
