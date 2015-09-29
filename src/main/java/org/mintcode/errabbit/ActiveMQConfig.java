package org.mintcode.errabbit;

/**
 * ActiveMQ Config
 * Created by soleaf on 9/29/15.
 */

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.mintcode.errabbit.core.collect.LogMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import java.net.UnknownHostException;

@Configuration
public class ActiveMQConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${errabbit.mq.url}")
    private String brokerURL;

    @Value(value = "${errabbit.mq.username ? : ''}")
    private String userName;

    @Value("${errabbit.mq.password ? : ''}")
    private String password;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private LogMessageListener listener;

    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory activeMQConnetionFactory
                = new ActiveMQConnectionFactory();
        activeMQConnetionFactory.setBrokerURL(brokerURL);
        if (!userName.isEmpty()){
            activeMQConnetionFactory.setUserName(userName);
        }
        if (!password.isEmpty()){
            activeMQConnetionFactory.setPassword(password);
        }
        activeMQConnetionFactory.setUseAsyncSend(true);
        return activeMQConnetionFactory;
    }

    @Bean

    public DefaultMessageListenerContainer defaultMessageListenerContainer()
            throws UnknownHostException {
        logger.info("Registering a defaultMessageListener.");
        final DefaultMessageListenerContainer messageListenerConatiner
                = new DefaultMessageListenerContainer();
        messageListenerConatiner.setConnectionFactory(connectionFactory);
        messageListenerConatiner.setDestination(new ActiveMQQueue("errabbit.report.>"));
        messageListenerConatiner.setMessageListener(listener);
        return messageListenerConatiner;
    }

}
