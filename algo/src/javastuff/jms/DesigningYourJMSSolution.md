1. Consider Caching or Pooling libraries Apache Commons Pool
    - protected ConnectionFactory connectionFactory;
    - protected Connection connection;
    - protected Session session;
    - protected MessageProducer messageProducer;
    - private Queue queue;
 2. HA & Thruput
 3. Message Ordering:
    - JMSXGroupID: only 1 consumer per JMSXGroupID
    - affects thruput
    - https://activemq.apache.org/how-do-i-preserve-order-of-messages.html