package org.scpsportfolio.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.scpsportfolio.backend.observer.implementation.StockDatabaseObserver;
import org.scpsportfolio.backend.observer.implementation.StockSubject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class StockService {

    private static final String FINNHUB_API_KEY = "cp9r04hr01qid7962rggcp9r04hr01qid7962rh0";
    private static final String FINNHUB_API_URL = "https://finnhub.io/api/v1/quote?symbol={symbol}&token=" + FINNHUB_API_KEY;
    private final StockRepository stockRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockSubject stockSubject = new StockSubject();


    // using constructor injection instead of @Autowired annotation (field injection)
    // because this improves testability and makes it clear what dependencies the class has
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @PostConstruct
    public void init() {
        stockSubject.addObserver(new StockDatabaseObserver(stockRepository));
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
            Stock stock = new Stock();
            stock.setSymbol(symbol);
            stock.setPrice(jsonNode.get("c").asDouble()); // the "c" field means current price
            stock.setTimestamp(System.currentTimeMillis());

            stockSubject.notifyObservers(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
