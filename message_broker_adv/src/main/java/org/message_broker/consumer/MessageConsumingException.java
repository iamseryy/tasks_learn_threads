package org.message_broker.consumer;

public final class MessageConsumingException extends RuntimeException {
    public MessageConsumingException() {

    }

    public  MessageConsumingException(final String description) {
        super(description);
    }

    public MessageConsumingException(final Exception cause) {
        super(cause);
    }

    public MessageConsumingException(final String description, final Exception cause) {
        super(description, cause);
    }
}
