package com.datacollector.rest;

import com.datacollector.service.DataCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/coins")
public class CoinsResource {

    private final DataCollectorService collectorService;

    @Autowired
    public CoinsResource(DataCollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCoinsList() {
        return collectorService.callCoingeckoApi("/coins/list");
    }

    @GetMapping(value = "markets", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCoinsMarkets(@RequestParam("vs_currency") String vs_currency) {
        return collectorService.callCoingeckoApi("/coins/markets", Map.of("vs_currency", vs_currency));
    }
}
