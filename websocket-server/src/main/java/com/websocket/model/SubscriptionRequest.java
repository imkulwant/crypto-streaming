package com.websocket.model;

import lombok.Data;

@Data
public class SubscriptionRequest {
    
    private final String sessionId;
    private final String destination;
    private final String requestCurrency;
    private final String userId;

}
