package com.websocket;

import com.websocket.model.AuthenticationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Service
public class ConnectionAuthenticator implements ApplicationListener<AuthenticationEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionAuthenticator.class);

    @Override
    public void onApplicationEvent(AuthenticationEvent authenticationEvent) {
        try {
            TextMessage authMessage = new TextMessage("{\"action\":\"auth\",\"params\":\"W0kXKlEz9aX8xkiGYw83urOD8JBGcmUp\"}");
            Thread.sleep(9000);
            authenticationEvent.getSession().sendMessage(authMessage);
            LOG.info("Authentication message sent");
        } catch (IOException | InterruptedException e) {
            LOG.error("Exception occurred while sending authentication message");
        }
    }
}
