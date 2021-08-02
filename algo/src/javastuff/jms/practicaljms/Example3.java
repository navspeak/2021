package javastuff.jms.practicaljms;

import javastuff.jms.practicaljms.base.AbstractExampleApplication;
import javastuff.jms.practicaljms.base.DelayingMessageListener;
import lombok.SneakyThrows;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@SuppressWarnings("Duplicates")
public class Example3 extends AbstractExampleApplication {


    public static void main(String... args) throws JMSException {
        Example3 example = new Example3();
        example.start();
    }

    private DefaultMessageListenerContainer container;

    @SneakyThrows
    private void start() throws JMSException {
        container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setConcurrentConsumers(5);
        container.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        container.setDestinationName("TEST_DESTINATION");
        container.setMessageListener(
                new DelayingMessageListener("Default", 10));
        container.setAutoStartup(true);
        container.initialize();
        container.start();
    }

    @SneakyThrows
    @Override
    public void shutdown() throws JMSException {
        container.stop();
        super.shutdown();
    }


}
