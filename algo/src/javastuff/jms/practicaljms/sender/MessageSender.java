package javastuff.jms.practicaljms.sender;

import javastuff.jms.practicaljms.base.AbstractExampleApplication;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class MessageSender extends AbstractExampleApplication {

    public static void main(String... args) throws Exception {
        //Sending of 100 messages is performed as part of the constructor
        MessageSender application = new MessageSender();
        application.shutdown();
    }




}
