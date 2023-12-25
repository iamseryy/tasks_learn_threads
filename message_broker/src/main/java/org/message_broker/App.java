package org.message_broker;

import org.message_broker.broker.MessageBroker;
import org.message_broker.consumer.MessageConsumingTask;
import org.message_broker.producer.MessageProducingTask;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        final int brokerMaxStoreMessages = 5;
        final MessageBroker messageBroker = new MessageBroker(brokerMaxStoreMessages);

        //final Thread producingThread = new Thread(new MessageProducingTask(messageBroker));
        final Thread consumingThread = new Thread(new MessageConsumingTask(messageBroker));

        //producingThread.start();
        consumingThread.start();
    }
}
