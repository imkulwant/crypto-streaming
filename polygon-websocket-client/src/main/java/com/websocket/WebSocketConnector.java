package com.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import java.io.IOException;

@Service
public class WebSocketConnector {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    private final WebSocketClient webSocketClient;
    private final MessageHandler webSocketHandler;

    @Autowired
    public WebSocketConnector(WebSocketClient webSocketClient, MessageHandler webSocketHandler) {
        this.webSocketClient = webSocketClient;
        this.webSocketHandler = webSocketHandler;
    }

    public void connect(String webSocketUrl) {
        try {
            WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(webSocketClient, webSocketHandler, webSocketUrl);
            connectionManager.start();
        } catch (Exception ex) {
            LOG.info("Exception occurred while making WebSocket connection [errorMessage={}]", ex.getMessage(), ex);
        }
    }

}