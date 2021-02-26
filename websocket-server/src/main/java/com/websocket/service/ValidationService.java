package com.websocket.service;

import com.datacollector.CoinData;
import com.datacollector.rest.CoinsResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidationService {

    private final CoinsResource coinsResource;

    private final ObjectMapper objectMapper;

    @Autowired
    public ValidationService(com.datacollector.rest.CoinsResource coinsResource) {
        this.coinsResource = coinsResource;
        this.objectMapper = new ObjectMapper();
    }


    public void validateRequestedCurrency(String destination) {
        try {
            String currency = getCurrency(destination);
            String coinsList = coinsResource.getCoinsList();

            List<CoinData> coinDataList = Arrays.asList(objectMapper.readValue(coinsList, CoinData[].class));

            boolean isCurrencyValid = coinDataList.stream().anyMatch(cd -> currency.equalsIgnoreCase(cd.getId()));

            if (!isCurrencyValid)
                throw new IllegalArgumentException("Invalid currency entered");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getCurrency(String destination) {

        String[] destinationSuffix = destination.split("reply/");

        Assert.isTrue((destinationSuffix.length > 1), "Enter valid currency");

        return destination.split("reply/")[1];
    }

}
