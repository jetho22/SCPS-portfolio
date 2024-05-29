package org.scpsportfolio.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.scpsportfolio.backend.factory.StockFactory;
import org.scpsportfolio.backend.model.Stock;
import org.scpsportfolio.backend.observer.implementation.StockDatabaseObserver;
import org.scpsportfolio.backend.observer.implementation.StockPublisher;
import org.scpsportfolio.backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class StockService {

    private static final String FINNHUB_API_KEY = ""; // hiding api key from git, need to implement .env file
    private static final String FINNHUB_API_URL = "https://finnhub.io/api/v1/quote?symbol={symbol}&token=" + FINNHUB_API_KEY;

    private final StockRepository stockRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final StockPublisher stockPublisher;
    private final StockFactory stockFactory;

    @Autowired
    public StockService(StockRepository stockRepository, StockFactory stockFactory, StockPublisher stockPublisher, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.stockRepository = stockRepository;
        this.stockFactory = stockFactory;
        this.stockPublisher = stockPublisher;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        stockPublisher.addObserver(new StockDatabaseObserver(stockRepository));
    }
    // is called every 12 seconds to not exceed the Finnhub API rate limit
    @Scheduled(fixedRate = 12000)
    public void fetchData() {
        List<String> symbols = Arrays.asList("AAPL", "TSLA", "AMZN", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT");

        for (String symbol : symbols) {
            fetchStockData(symbol);
        }
    }

    private void fetchStockData(String symbol) {
        String url = FINNHUB_API_URL.replace("{symbol}", symbol);
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            Stock stock = stockFactory.createStock(
                    symbol,
                    jsonNode.get("c").asDouble(),
                    System.currentTimeMillis()
            );
            stockPublisher.notifyObservers(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
