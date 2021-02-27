package com.websocket.service;

import com.datacollector.model.CoinData;
import com.datacollector.service.CoinsDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.model.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final ObjectMapper objectMapper;
    private final CoinsDataService coinsDataService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageSender(ObjectMapper objectMapper, CoinsDataService coinsDataService, SimpMessagingTemplate simpMessagingTemplate) {
        this.objectMapper = objectMapper;
        this.coinsDataService = coinsDataService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessage(SubscriptionRequest subscriptionRequest) {

        String message = getWebSocketMessage(subscriptionRequest);
        simpMessagingTemplate.convertAndSend(subscriptionRequest.getDestination(), message);

    }

    private String getWebSocketMessage(SubscriptionRequest subscriptionRequest) {
        CoinData coinUpdate = coinsDataService.getCurrentData(subscriptionRequest.getRequestCurrency());
        try {
            return objectMapper.writeValueAsString(coinUpdate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException occurred ", e);
        }
    }
}
