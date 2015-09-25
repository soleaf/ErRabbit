package org.mintcode.errabbit;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * Web socket security configuration
 * Created by soleaf on 8/1/15.
 */

@Configurable
public class WebSocketSecurityConfig extends
        AbstractSecurityWebSocketMessageBrokerConfigurer {
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/topic/console").authenticated();

    }
}
