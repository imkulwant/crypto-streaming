package com.datacollector.rest;

import com.datacollector.model.CoinData;
import com.datacollector.service.CoinsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoinsResource {

    private final CoinsDataService coinsDataService;

    @Autowired
    public CoinsResource(CoinsDataService coinsDataService) {
        this.coinsDataService = coinsDataService;
    }

    @GetMapping(value = "coins/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CoinData> getCoinsList() {
        return coinsDataService.getCoinsList();
    }

    @GetMapping(value = "coins/markets", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCoinsMarkets(@RequestParam("vs_currency") String vs_currency) {
        return coinsDataService.getCoinsMarkets(vs_currency);
    }

    @GetMapping(value = "coins/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CoinData getCurrentData(@PathVariable final String id) {
        return coinsDataService.getCurrentData(id);
    }
}
