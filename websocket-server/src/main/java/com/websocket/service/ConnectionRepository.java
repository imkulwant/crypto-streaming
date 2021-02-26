package com.websocket.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class ConnectionRepository {

    private HashMap<String, String> activeSubscriptions = new HashMap<>();

    public HashMap<String, String> getActiveSubscriptions() {
        return activeSubscriptions;
    }

    public void addActiveSubscriptions(String sessionId, String destination) {
        this.activeSubscriptions.put(sessionId, destination);
    }

    public void removeActiveSubscriptions(String sessionId) {
        this.activeSubscriptions.remove(sessionId);
    }

}
