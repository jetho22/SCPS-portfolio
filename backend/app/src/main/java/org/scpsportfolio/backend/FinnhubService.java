package org.scpsportfolio.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class FinnhubService {

    private static final String FINNHUB_API_KEY = "cp9k31pr01qid795kabgcp9k31pr01qid795kac0";
    private static final String FINNHUB_API_URL = "https://finnhub.io/api/v1/quote?symbol={symbol}&token=" + FINNHUB_API_KEY;

    @Autowired
    private TradeRepository tradeRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Trade> tradeBuffer = new ArrayList<>();

    @Scheduled(fixedRate = 1000)
    public void fetchData() {
        fetchTradeData("AAPL");
        fetchTradeData("BINANCE:BTCUSDT");

        flushTrades();
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

            synchronized (tradeBuffer) {
                tradeBuffer.add(trade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flushTrades() {
        synchronized (tradeBuffer) {
            if (!tradeBuffer.isEmpty()) {
                tradeRepository.saveAll(tradeBuffer);
                tradeBuffer.clear();
            }
        }
    }
}
