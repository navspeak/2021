1. Create a Connection Factory
```java
public class Application {

   /* CONNECTION FACTORY */
    public ConnectionFactory createConnectionFactory(){
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
        //failover("tcp://localhost:61616", "tcp://localhost:61616")
    }
    public XAConnectionFactory createXAConnectionFactory(){
        return new ActiveMQXAConnectionFactory("tcp://localhost:61616");
    }
    public QueueConnectionFactory createQueueConnectionFactory(){
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }
   public XAQueueConnectionFactory createXAQueueConnectionFactory(){
        return new ActiveMQXAConnectionFactory("tcp://localhost:61616");
    }
    public TopicConnectionFactory createTopicConnectionFactory(){
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }
    public XATopicConnectionFactory createXATopicConnectionFactory(){
        return new ActiveMQXAConnectionFactory("tcp://localhost:61616");
    }
//...
}
```
2. Create a Connection
```java
public class Application {
    
    public Connection createConnection(ConnectionFactory cf) throws JMSException{
	return cf.createConnection();
    }
    public XAConnection createXAConnection(ConnectionFactory cf) throws JMSException{
	return cf.createXAConnection();
    }
    public QueueConnection createQueueConnection(QueueConnectionFactory cf) throws JMSException{
	return cf.createQueueConnection();
    }

    public XAQueueConnection createXAQueueConnection(XAQueueConnectionFactory cf) throws JMSException{
	return cf.createXAQueueConnection();
    }
    public TopicConnection createTopicConnection(TopicConnectionFactory cf) throws JMSException{
	return cf.createTopicConnection();
    }
    public XATopicConnection createXATopicConnection(XATopicConnectionFactory cf) throws JMSException{
	return cf.createXATopicConnection();
    }
}
```
3. Create a Session ( if Transacted, Ack Mode: auto, dups ok, client ack)
```java
public class Application {

    public Session createSession(Connection conn) throws JMSException{
	    return conn.createSession(/*transacted =*/false, Session.AUTO_ACKNOWLEDGE);   
    }
    public XASession createXASession(XAConnection conn) throws JMSException{
	    return conn.createXASession(/*No params needed*/);
    }
    public QueueSession createQueueSession(QueueConnection conn) throws JMSException{
	    return conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    public XAQueueSession createXAQueueSession(QueueConnection conn) throws JMSException{
	    return conn.createXAQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public TopicSession createTopicSession(TopicConnection conn) throws JMSException{
	    return conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    public XATopicSession createXATopicSession(TopicConnection conn) throws JMSException{
	    return conn.createXATopicSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}
```
4. Transactions and Ack: No need to worry about ack mode if transacted.
    - conn.createSession(/*transacted =*/true, Session.SESSION_TRANSACTED); 
    - SESSION_TRANSACTED = 0, 
    - AUTO_ACK =1 (Note all messages in session will be auto ack not per msg whient consumed via receive())
    - CLIENT_ACK : consumer code calls Message.acknowlege() explicitly.
    - DUPS_OK_ACK = 3 
        - acks are buffered up in the consumer before all being sent at the same time to reduce the amount of network traffic. 
        - However, should the client system shut down, then the acks'll be lost &  the messages will be re-dispatched & processed a second time. 
        - The code must therefore deal with the likelihood of duplicate messages.
5.  Sending Message To Queue  
 ```java
 public class Application {
//Generic
   public void sendTextMsgToQ (String msg, Session session) throws JMSException {
     Queue queue = session.createQueue("TEST_DEST");
     TextMessage msg = session.createTextMessage(msg);
     MessageProducer msgProducer = session.createProducer(queue);
     msgProducer.send(msg);
   }
   public static void main(String[] args){
     Application app = new Application();
     ConnectionFactory cf = app.createConnectionFactory();
     ConnectionFactory conn = app.createConnection();
     Session session = app.createSession(conn);
     app.sendTextMsgToQ("Test msg", session);
     session.close();
     conn.close();
   }
//QueueSender and QueueSession
   public void sendTextMsgToQ (String msg, QueueSession session) throws JMSException {
     Queue queue = session.createQueue("TEST_DEST");
     TextMessage msg = session.createTextMessage(msg);
     //MessageProducer msgProducer = session.createProducer(queue); - could leave as is
     QueueSender msgProducer = session.createSender(queue);
     msgProducer.send(msg);
   }
   public static void main_v2(String[] args){
     Application app = new Application();
     QueueConnectionFactory cf = app.createQueueConnectionFactory();
     QueueConnectionFactory conn = app.createQueueConnection();
     QueueSession session = app.createQueueSession(conn);
     app.sendTextMsgToQ("Another Test msg", session);
     session.close();
     conn.close();
   }
}
```

6. Sending message to Topic
```java
 public class Application {
//Generic
   public void sendTextMsgToTopicQ (String msg, Session session) throws JMSException {
     Topic topic = session.createTopic("TEST_Topoc");
     TextMessage msg = session.createTextMessage(msg);
     MessageProducer msgProducer = session.createProducer(topic);
     msgProducer.send(msg);
   }
   public static void main(String[] args){
     Application app = new Application();
     ConnectionFactory cf = app.createConnectionFactory(cf);
     ConnectionFactory conn = app.createConnection();
     Session session = app.createSession(conn);
     app.sendTextMsgToTopic("Test msg", session);
     session.close();
     conn.close();
   }
// TopicSession
  public void sendTextMsgToTopic (String msg, TopicSession session) throws JMSException {
     Topic topic = session.createTopic("TEST_TOPIC");
     TextMessage msg = session.createTextMessage(msg);
     TopicSender topicPublisher = session.createPublisher(topic);
     topicPublisher.send(msg);
   }
    public static void main_v2(String[] args){
     Application app = new Application();
     TopicConnectionFactory cf = app.createTopicConnectionFactory();
     TopicConnectionFactory conn = app.createTopicConnection(cf);
     TopicSession session = app.createTopicSession(conn);
     app.sendTextMsgToTopic("Another Test msg", session);
     session.close();
     conn.close();
   }
}
```
 7. Consuming from a Queue
 ```java
  public class Application {  
  //uses polling
  public void consumeFromDestination(Session session, String destination) throws JMSException{
     Queue queue = session.createQueue("TEST_DEST");
     MessageConsumer msgConsumer = session.createConsumer(queue);
     boolean someCondition = false;
     while(someCondition!= true){
         Message msg = msgConsumer.receive(/*timeout*/ 500); // if timeout is not passed will block
         if (null != msg){ // do smthg 
         }
     }
  }
  // Listener
  public MessageConsumer consumeFromDestination(Session session, String destination, MessageListener listener) throws JMSException{
     Queue queue = session.createQueue("TEST_DEST");
     MessageConsumer consumer = session.createConsumer(queue);
     consumer.setMessageListener(listener);
     return consumer;
  }

   public static void main(String[] args){
     Application app = new Application();
     ConnectionFactory cf = app.createConnectionFactory();
     ConnectionFactory conn = app.createConnection(cf);
     Session session = app.createSession(conn);
     MessageConsumer consumer= app.consumeFromQueue(session, "TEST_DEST", message -> {/*do smthg*/});
     conn.start();
     Thread shutDownTask = () -> {
            conn.start();
            consumer.close();
            session.close();
            conn.close();
     };
     Runtime.getRuntime().addShutdownHook(shutDownTask);
     }
   }
```
 7. Consuming from a Topic
 ```java
   public class Application {  
   //uses polling
  public MessageConsumer consumeFromTopic(Session session, String destination, MessageListener listener) throws JMSException{
     Topic topic = session.createTOPIC("TEST_TOPIC");
     MessageConsumer consumer = session.createConsumer(topic);
     consumer.setMessageListener(listener);
     return consumer;
  }

 public static void main(String[] args){
     Application app = new Application();
     ConnectionFactory cf = app.createConnectionFactory();
     ConnectionFactory conn = app.createConnection(cf);
     Session session = app.createSession(conn);
     MessageConsumer consumer= app.consumeFromTopic(session, "TEST_TOPIC", message -> {/*do smthg*/});
     conn.start();
     Thread shutDownTask = () -> {
            conn.start();
            consumer.close();
            session.close();
            conn.close();
     };
     Runtime.getRuntime().addShutdownHook(shutDownTask);
     }
   }
```
 8. Consuming Durable subscription: If you aint actively consuming message you'll lose it
  ```java
    public class Application {  
    //uses polling
   public MessageConsumer consumeFromTopic(Session session, String destination, MessageListener listener) throws JMSException{
      Topic topic = session.createTOPIC("TEST_TOPIC");
      TopicSubscriber consumer = session.createDurableSubscriber(topic, "test-subscribtion");
      consumer.setMessageListener(listener);
      return consumer;
   }

 public static void main(String[] args){
     Application app = new Application();
     ConnectionFactory cf = app.createConnectionFactory();
     ConnectionFactory conn = app.createConnection(cf);
     Session session = app.createSession(conn);
     conn.setClientID("MyUniqueId");
     TopicSubscriber consumer= app.consumeFromTopic(session, "TEST_TOPIC", message -> {/*do smthg*/});
     conn.start();
     Thread shutDownTask = () -> {
            conn.start();
            consumer.close();
            session.close(); // let broker know that it has to keep the message till the consumer comes up
            conn.close();
            // if u are finsiged subscribing
            session.unsubscribe("test-subscribtion");
     };
     Runtime.getRuntime().addShutdownHook(shutDownTask);
     }
   }
```
9. Message Priorities and expiration
```java
class Appication{
  public void sendTextMsgToTopic (String msg, TopicSession session) throws JMSException {
     Topic topic = session.createTopic("TEST_TOPIC");
     TextMessage msg = session.createTextMessage(msg);
     MessageProducer msgProducer = session.createProducer(topic);
     msgProducer.setPriority(9); //0-4 normal. 5-9 expidited. 4 default
     msgProducer.setTimeToLive(1000); //ms, 0 fefault, doesnt expire
     msgProducer.send(msg, // per msg basis
                DeliverMode.NON_PERSISTENT,
               9, //priority
               0); // timeToLive
   }
}
```