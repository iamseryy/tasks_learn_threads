package org.message_broker;

import org.message_broker.broker.MessageBroker;
import org.message_broker.consumer.MessageConsumingTask;
import org.message_broker.producer.MessageFactory;
import org.message_broker.producer.MessageProducingTask;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        final int brokerMaxStoreMessages = 15;
        final MessageBroker messageBroker = new MessageBroker(brokerMaxStoreMessages);
        final MessageFactory messageFactory = new MessageFactory();

        final Thread firstProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory, brokerMaxStoreMessages, "PRODUCER_1"));
        final Thread secondProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory, 10, "PRODUCER_2"));
        final Thread thirdProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory, 5, "PRODUCER_3"));

        final Thread firstConsumingThread = new Thread(new MessageConsumingTask(messageBroker, 0, "CONSUMER_1"));
        final Thread secondConsumingThread = new Thread(new MessageConsumingTask(messageBroker, 6, "CONSUMER_2" ));
        final Thread thirdConsumingThread = new Thread(new MessageConsumingTask(messageBroker, 11, "CONSUMER_3"));

        startThreads(firstProducingThread, secondProducingThread, thirdProducingThread, firstConsumingThread,
                secondConsumingThread, thirdConsumingThread);
    }

    private static void startThreads(final Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }
}
