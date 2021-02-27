package com.websocket.service;

import com.datacollector.model.CoinData;
import com.datacollector.service.CoinsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ValidationService {

    private final CoinsDataService coinsDataService;

    @Autowired
    public ValidationService(CoinsDataService coinsDataService) {
        this.coinsDataService = coinsDataService;
    }


    public void validateRequestedCurrency(String destination) {

        String currency = getCurrency(destination);
        List<CoinData> coinsList = coinsDataService.getCoinsList();

        boolean isCurrencyValid = coinsList.stream().anyMatch(cd -> currency.equalsIgnoreCase(cd.getId()));

        if (!isCurrencyValid)
            throw new IllegalArgumentException("Invalid currency entered");


    }

    private String getCurrency(String destination) {

        String[] destinationSuffix = destination.split("reply/");

        Assert.isTrue((destinationSuffix.length > 1), "Enter valid currency");

        return destination.split("reply/")[1];
    }

}
