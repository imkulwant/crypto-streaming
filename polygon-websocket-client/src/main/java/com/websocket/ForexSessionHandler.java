package com.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

public class ForexSessionHandler implements StompSessionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ForexSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOG.info("WebSocket connection established [sessionId={}]", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOG.error("Got an exception [errorMessage={}]", exception.getMessage(), exception);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        LOG.error("Got a transport error [errorMessage={}]", throwable.getMessage(), throwable);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String msg = (String) payload;
        LOG.info("Received message: [payload={}]", msg);
    }

}