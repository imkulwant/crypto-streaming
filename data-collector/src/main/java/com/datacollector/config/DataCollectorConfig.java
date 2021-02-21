package com.datacollector.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
@ComponentScan("com.datacollector")
public class DataCollectorConfig {

    @Value("${webclient.max-in-memory-size}")
    private int maxInMemorySize;

    @Value("${httpclient.connect-time-out}")
    private int connectTimeOut;

    @Value("${httpclient.response-time-out}")
    private int responseTimeOut;

    @Value("${httpclient.read-time-out}")
    private int readTimeOut;

    @Value("${httpclient.write-time-out}")
    private int writeTimeOut;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs()
                                .maxInMemorySize(maxInMemorySize))
                        .build())
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeOut)
                .responseTimeout(Duration.ofMillis(responseTimeOut))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(readTimeOut, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeOut, TimeUnit.MILLISECONDS)));
    }

}
