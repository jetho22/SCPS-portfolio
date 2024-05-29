package org.scpsportfolio.backend.observer.implementation;

import org.scpsportfolio.backend.model.Stock;
import org.scpsportfolio.backend.repository.StockRepository;
import org.scpsportfolio.backend.observer.StockObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockDatabaseObserver implements StockObserver {
    Logger logger = LoggerFactory.getLogger(StockDatabaseObserver.class);
    private final StockRepository stockRepository;

    public StockDatabaseObserver(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Override
    public void update(Stock stock) {
        stockRepository.save(stock);
        logger.info("Stock saved to database: {}", stock);
    }
}
