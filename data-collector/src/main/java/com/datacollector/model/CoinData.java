package com.datacollector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinData {

    private String id;
    private String symbol;
    private String name;

    private String hashing_algorithm;

    private Map<String, String> image;
    private String genesis_date;
    private Long sentiment_votes_up_percentage;
    private Long sentiment_votes_down_percentage;

    private Long market_cap_rank;
    private Long coingecko_rank;
    private Long coingecko_score;
    private Long developer_score;
    private Long community_score;
    private Long liquidity_score;
    private Long public_interest_score;

    private Map<String, Object> market_data;
    private Map<String, Object> community_data;
    private Map<String, Object> developer_data;

    private String last_updated;

    private List<Map<String, Object>> tickers;

}
