package com.datacollector.rest;

import com.datacollector.service.DataCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CoinsResource {

    private final DataCollectorService collectorService;

    @Autowired
    public CoinsResource(DataCollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @GetMapping(value = "coins/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCoinsList() {
        return collectorService.callCoingeckoApi("/coins/list");
    }

    @GetMapping(value = "coins/markets", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCoinsMarkets(@RequestParam("vs_currency") String vs_currency) {
        return collectorService.callCoingeckoApi("/coins/markets", Map.of("vs_currency", vs_currency));
    }

    @GetMapping(value = "coins/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCurrentData(@PathVariable final String id) {
        return collectorService.callCoingeckoApi(String.format("/coins/%s", id));
    }
}
