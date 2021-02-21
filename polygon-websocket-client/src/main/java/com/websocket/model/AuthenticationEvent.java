package com.websocket.model;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class AuthenticationEvent extends ApplicationEvent {

    private final WebSocketSession session;

    public AuthenticationEvent(WebSocketSession session) {
        super(session);
        this.session = session;
    }

    public WebSocketSession getSession() {
        return session;
    }
}
