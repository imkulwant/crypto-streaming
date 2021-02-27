package com.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SubscriptionRequestService subscriptionRequestService;

    @Autowired
    public WebSocketEventListener(SubscriptionRequestService subscriptionRequestService) {
        this.subscriptionRequestService = subscriptionRequestService;
    }


    @EventListener
    public void onSessionSubscribeEvent(SessionSubscribeEvent sessionSubscribeEvent) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());

        String sessionId = headerAccessor.getSessionId();

        subscriptionRequestService.processSubscriptionRequest(sessionSubscribeEvent);

        LOG.info("Session connected [sessionId={}]", sessionId);
    }

    @EventListener
    public void onSessionDisconnectEvent(SessionDisconnectEvent sessionDisconnectEvent) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());

        String sessionId = headerAccessor.getSessionId();

        subscriptionRequestService.removeActiveSubscriptions(sessionId);

        LOG.info("Session disconnected [sessionId={}]", sessionId);
    }


}