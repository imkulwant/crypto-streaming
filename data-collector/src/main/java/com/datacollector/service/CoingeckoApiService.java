package com.datacollector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class CoingeckoApiService {

    private static final Logger LOG = LoggerFactory.getLogger(CoingeckoApiService.class);

    @Value("${coingecko.base-url}")
    private String coingeckoBaseUrl;

    private final WebClient webClient;

    @Autowired
    public CoingeckoApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String callCoingeckoApi(String resourcePath) {
        UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(coingeckoBaseUrl).path(resourcePath);

        return webClient.get()
                .uri(uriBuilder -> uriComponents.build().toUri())
                .exchangeToMono(response -> {
                    if (HttpStatus.OK.equals(response.statusCode())) {
                        return response.bodyToMono(String.class);
                    } else {
                        LOG.error("Coingecko API returned error [code={}]", response.statusCode());
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                }).block(Duration.ofMillis(6000));
    }

    public String callCoingeckoApi(String resourcePath, Map<String, String> requestParams) {

        UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(coingeckoBaseUrl).path(resourcePath);

        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            uriComponents.queryParam(param.getKey(), param.getValue());
        }

        return webClient.get()
                .uri(uriComponents.build().toUri())
                .exchangeToMono(response -> {
                    if (HttpStatus.OK.equals(response.statusCode())) {
                        return response.bodyToMono(String.class);
                    } else {
                        LOG.error("Coingecko API returned error [code={}]", response.statusCode());
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                }).block(Duration.ofMillis(6000));
    }

}
