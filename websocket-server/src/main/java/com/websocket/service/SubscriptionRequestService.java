package com.websocket.service;

import com.datacollector.model.CoinData;
import com.datacollector.service.CoinsDataService;
import com.websocket.model.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class SubscriptionRequestService {

    private final CoinsDataService coinsDataService;

    private Set<SubscriptionRequest> activeSubscriptions = new CopyOnWriteArraySet<>();


    @Autowired
    public SubscriptionRequestService(CoinsDataService coinsDataService) {
        this.coinsDataService = coinsDataService;
    }


    public void processSubscriptionRequest(SessionSubscribeEvent sessionSubscribeEvent) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());

        String sessionId = headerAccessor.getSessionId();
        String destination = headerAccessor.getDestination();

        String currency = validateSubscriptionRequest(destination);
        String userId = headerAccessor.getUser().getName();

        addActiveSubscriptions(sessionId, destination, currency, userId);


    }

    private String validateSubscriptionRequest(String destination) {
        String currency = getCurrency(destination);
        List<CoinData> coinsList = coinsDataService.getCoinsList();

        boolean isCurrencyValid = coinsList.stream().anyMatch(cd -> currency.equalsIgnoreCase(cd.getId()));

        if (!isCurrencyValid)
            throw new IllegalArgumentException("Invalid currency entered");

        return currency;
    }

    private String getCurrency(String destination) {

        String[] destinationSuffix = destination.split("reply/");

        Assert.isTrue((destinationSuffix.length > 1), "Enter valid currency");

        return destination.split("reply/")[1];
    }


    public Set<SubscriptionRequest> getActiveSubscriptions() {
        return activeSubscriptions;
    }

    public void addActiveSubscriptions(String sessionId, String destination, String currency, String userId) {
        this.activeSubscriptions.add(new SubscriptionRequest(sessionId, destination, currency, userId));
    }

    public void removeActiveSubscriptions(String sessionId) {
        this.activeSubscriptions.removeIf(sub -> sessionId.equals(sub.getSessionId()));
    }

}
