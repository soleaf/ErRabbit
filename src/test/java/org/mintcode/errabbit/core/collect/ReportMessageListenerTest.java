//package org.mintcode.errabbit.core.collect;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggingEvent;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mintcode.errabbit.core.report.dao.ReportRepository;
//import org.mintcode.errabbit.model.ErrLoggingEvent;
//import org.mintcode.errabbit.model.Report;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import static org.junit.Assert.*;
//
//import javax.jms.*;
//import java.io.IOException;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"
//})
//public class ReportMessageListenerTest {
//
//    private final String rabbitId = "test_rabbit";
//
//    @Autowired
//    ReportRepository reportRepository;
//
//    @Test
//    public void testOnMessage() throws Exception {
//
//
//        //created ConnectionFactory object for creating connection
//        String userName = "sender";
//        String password = "senderpassword!";
//
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName,password,"tcp://localhost:61616");
//
//        // Create a Connection
//        Connection connection = factory.createConnection();
//        connection.start();
//
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//        // Create the destination (Topic or Queue)
//        Destination destination = session.createQueue("errabbit.report");
//
//        // Create a MessageProducer from the Session to the Topic or Queue
//        MessageProducer producer = session.createProducer(destination);
//        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//
//        // Create a messages
//        ObjectMessage message = session.createObjectMessage(makeMessage());
//
//        // Tell the producer to send the message
//        System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
//        producer.send(message);
//
//        // Waiting for Listener
//        Thread.sleep(2000);
//
//        // Check Report was inserted repository.
//        assertNotNull(reportRepository);
//        PageRequest pageRequest = new PageRequest(0,1, Sort.Direction.DESC, "_id");
//        Page<Report> reportPage = reportRepository.findByRabbitId(rabbitId, pageRequest);
//        assertNotNull(reportPage);
//        assertFalse(reportPage.getContent().isEmpty());
//
//    }
//
//    public Report makeMessage(){
//
//        Logger logger = Logger.getLogger(getClass());
//        LoggingEvent le = new LoggingEvent(
//                "org.mintcode.errabbit.test.object",
//                logger, Level.DEBUG, null, new IOException("It's Test Exception"));
//        return new Report("test_rabbit", ErrLoggingEvent.fromLoggingEvent(le));
//
//    }
//
//}