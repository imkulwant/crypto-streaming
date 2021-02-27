package com.datacollector.service;

import com.datacollector.model.CoinData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CoinsDataService {

    private final CoingeckoApiService apiService;

    private final ObjectMapper objectMapper;

    @Autowired
    public CoinsDataService(CoingeckoApiService apiService, ObjectMapper objectMapper) {
        this.apiService = apiService;
        this.objectMapper = objectMapper;
    }

    public List<CoinData> getCoinsList() {
        try {
            return Arrays.asList(objectMapper.readValue(apiService.callCoingeckoApi("/coins/list"), CoinData[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException occurred [message={}]", e);
        }
    }

    public String getCoinsMarkets(String vs_currency) {
        return apiService.callCoingeckoApi("/coins/markets", Map.of("vs_currency", vs_currency));
    }

    public CoinData getCurrentData(String id) {
        try {
            return objectMapper.readValue(apiService.callCoingeckoApi(String.format("/coins/%s", id)), CoinData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException occurred [message={}]", e);
        }
    }
}
