package org.mintcode.errabbit.core.collect;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Report;

import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soleaf on 2015. 2. 4..
 */
public class DummyCollect {

    public static void send() throws JMSException, InterruptedException {


        //created ConnectionFactory object for creating connection

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("errabbit.report");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        ObjectMessage message = session.createObjectMessage(makeMessage());

        // Tell the producer to send the message
        System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);

        // Clean up
        producer.close();
        session.close();
        connection.close();

        // Waiting for Listener
        Thread.sleep(2000);

    }


    public static Report makeMessage() {

        Logger logger = Logger.getLogger(DummyCollect.class);
        LoggingEvent le = new LoggingEvent(
                "org.mintcode.errabbit.test.object",
                logger, Level.DEBUG, null, new IOException("It's Test Exception"));
        return new Report("test_rabbit", ErrLoggingEvent.fromLogingEvent(le));

    }
}
