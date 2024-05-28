package org.scpsportfolio.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {


    private final TradeRepository tradeRepository;
    // using constructor injection instead of @Autowired annotation (field injection)
    // because this improves testability and makes it clear what dependencies the class has
    public TradeController(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @GetMapping("/{symbol}")
    public Trade getLatestTrade(@PathVariable String symbol) {
        return tradeRepository.findTopBySymbolOrderByTimestampDesc(symbol);
    }
}