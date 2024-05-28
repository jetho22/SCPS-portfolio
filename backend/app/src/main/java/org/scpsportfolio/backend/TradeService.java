package org.scpsportfolio.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.scpsportfolio.backend.observer.implementation.TradeDatabaseObserver;
import org.scpsportfolio.backend.observer.implementation.TradeSubject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class TradeService {

    private static final String FINNHUB_API_KEY = "cp9r04hr01qid7962rggcp9r04hr01qid7962rh0";
    private static final String FINNHUB_API_URL = "https://finnhub.io/api/v1/quote?symbol={symbol}&token=" + FINNHUB_API_KEY;
    private final TradeRepository tradeRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final List<Trade> tradeBuffer = new ArrayList<>();
    private final TradeSubject tradeSubject = new TradeSubject();


    // using constructor injection instead of @Autowired annotation (field injection)
    // because this improves testability and makes it clear what dependencies the class has
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @PostConstruct
    public void init() {
        tradeSubject.addObserver(new TradeDatabaseObserver(tradeRepository));
    }
    // is called every 12 seconds to not exceed the Finnhub API rate limit
    @Scheduled(fixedRate = 12000)
    public void fetchData() {
        List<String> symbols = Arrays.asList("AAPL", "TSLA", "AMZN", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT");

        for (String symbol : symbols) {
            fetchTradeData(symbol);
        }
//        flushTrades();
    }

    private void fetchTradeData(String symbol) {
        String url = FINNHUB_API_URL.replace("{symbol}", symbol);
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            Trade trade = new Trade();
            trade.setSymbol(symbol);
            trade.setPrice(jsonNode.get("c").asDouble());
            trade.setVolume(0);  // Volume data may not be available from quote endpoint
            trade.setTimestamp(System.currentTimeMillis());

//            synchronized (tradeBuffer) {
//                tradeBuffer.add(trade);
//            }

            tradeSubject.notifyObservers(trade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void flushTrades() {
//        synchronized (tradeBuffer) {
//            if (!tradeBuffer.isEmpty()) {
//                tradeRepository.saveAll(tradeBuffer);
//                tradeBuffer.clear();
//            }
//        }
//    }
}
