package com.websocket.client;

import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.common.scopes.SimpleContainerScope;
import org.eclipse.jetty.websocket.jsr356.ClientContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketClientConfig {

    @Bean
    public WebSocketHandler webSocketHandler() throws Exception {
        return new TextWebSocketHandler();
    }

    @Bean
    public WebSocketClient webSocketClient() throws Exception {
        return new StandardWebSocketClient(clientContainer());
    }

    public ClientContainer clientContainer() throws Exception {
        WebSocketPolicy webSocketPolicy = WebSocketPolicy.newClientPolicy();
        ClientContainer clientContainer = new ClientContainer(new SimpleContainerScope(webSocketPolicy));
        clientContainer.start();
        return clientContainer;
    }
}
