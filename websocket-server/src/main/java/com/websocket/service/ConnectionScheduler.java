package com.websocket.service;

import com.websocket.model.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@EnableScheduling
public class ConnectionScheduler {

    private final MessageSender messageSender;
    private final SubscriptionRequestService subscriptionRequestService;

    @Autowired
    public ConnectionScheduler(MessageSender messageSender, SubscriptionRequestService subscriptionRequestService) {
        this.messageSender = messageSender;
        this.subscriptionRequestService = subscriptionRequestService;
    }


    @Scheduled(cron = "0/6 * * * * ?")
    public void checkForOpenConnections() {
        Set<SubscriptionRequest> openConnections = subscriptionRequestService.getActiveSubscriptions();

        openConnections.forEach(connection -> {

            messageSender.sendMessage(connection);

        });
    }
}
