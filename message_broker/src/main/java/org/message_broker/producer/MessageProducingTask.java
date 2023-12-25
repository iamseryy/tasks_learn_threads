package org.message_broker.producer;

import org.message_broker.broker.MessageBroker;
import org.message_broker.model.Message;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public final class MessageProducingTask implements Runnable {
    private static final String MESSAGE_PRODUCE = "Message %s is produced.\n";
    private static final int SLEEP_BEFORE_PRODUCING = 1;
    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;

    public MessageProducingTask (final MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
        this.messageFactory = new MessageFactory();

    }

    @Override
    public void run() {
        try {
                while (!Thread.currentThread().isInterrupted()) {
                    final Message message = this.messageFactory.create();
                    TimeUnit.SECONDS.sleep(SLEEP_BEFORE_PRODUCING);
                    this.messageBroker.produce(message);
                    System.out.printf(MESSAGE_PRODUCE, message);
                }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class MessageFactory {
        private static final int INITIAL_NEXT_MESSAGE_INDEX = 1;
        private static final String TEMPLATE_MESSAGE = "Message#%d";
        private int nextMessageIndex;

        public MessageFactory() {
            this.nextMessageIndex = INITIAL_NEXT_MESSAGE_INDEX;
        }

        public Message create(){
            return new Message(format(TEMPLATE_MESSAGE, this.nextMessageIndex++));
        }
    }
}
