package org.message_broker.producer;

import org.message_broker.model.Message;

import static java.lang.String.format;

public final class MessageFactory {
    private static final int INITIAL_NEXT_MESSAGE_INDEX = 1;
    private static final String TEMPLATE_MESSAGE = "Message#%d";
    private int nextMessageIndex;

    public MessageFactory() {
        this.nextMessageIndex = INITIAL_NEXT_MESSAGE_INDEX;
    }

    public Message create(){
        return new Message(format(TEMPLATE_MESSAGE, this.incrementNextMessageIndex()));
    }

    private synchronized int incrementNextMessageIndex() {
        return this.nextMessageIndex++;
    }
}
