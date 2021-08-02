package javastuff.jms.practicaljms;

import javastuff.jms.practicaljms.base.AbstractExampleApplication;
import javastuff.jms.practicaljms.base.DelayingMessageListener;

import javax.jms.*;

/**
 * This example shows the use of a message selector
 *
 * Created by Grant Little grant@grantlittle.me
 */
public class Example7 extends AbstractExampleApplication {


    public static void main(String... args) throws Exception {
        Example7 example = new Example7();
        example.start();
    }

    private MessageConsumer messageConsumer;

    private void start() throws JMSException {
        MessageListener messageListener =
                new DelayingMessageListener("Consumer", 100);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("TEST_DESTINATION");
        messageConsumer = session
                .createConsumer(queue, "type = 'CreateOrder'");
        messageConsumer.setMessageListener(messageListener);

        connection.start();
    }

    @Override
    protected void sendMessages() throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        messageProducer = session.createProducer(queue);

        TextMessage createOrderMessage = session
                .createTextMessage("Create Order Message");
        createOrderMessage.setStringProperty("type", "CreateOrder");
        messageProducer.send(createOrderMessage);

        TextMessage updateOrderMessage = session
                .createTextMessage("Update Order Message");
        updateOrderMessage.setStringProperty("type", "UpdateOrder");
        messageProducer.send(updateOrderMessage);
    }

    @Override
    public void shutdown() throws JMSException {
        messageConsumer.close();
        session.close();
        super.shutdown();
    }


}
