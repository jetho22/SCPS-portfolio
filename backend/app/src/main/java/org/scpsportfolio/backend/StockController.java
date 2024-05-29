package org.scpsportfolio.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {


    private final StockRepository stockRepository;
    // using constructor injection instead of @Autowired annotation (field injection)
    // because this improves testability and makes it clear what dependencies the class has
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