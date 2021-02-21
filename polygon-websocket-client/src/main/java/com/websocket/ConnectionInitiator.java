package com.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConnectionInitiator {

    private final WebSocketConnector webSocketConnector;

    @Value("${polygon.websockets.forex.url}")
    private String forexWebSocketUrl;

    @Autowired
    public ConnectionInitiator(WebSocketConnector webSocketConnector) {
        this.webSocketConnector = webSocketConnector;
    }

    @PostConstruct
    public void initiateConnections() {
        //webSocketConnector.connect(forexWebSocketUrl);
    }
}
