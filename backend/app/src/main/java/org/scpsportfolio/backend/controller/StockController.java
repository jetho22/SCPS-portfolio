package org.scpsportfolio.backend.controller;

import org.scpsportfolio.backend.model.Stock;
import org.scpsportfolio.backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    @Autowired
    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @GetMapping("/{symbol}")
    public Stock getLatestStock(@PathVariable String symbol) {
        return stockRepository.findTopBySymbolOrderByTimestampDesc(symbol);
    }
}