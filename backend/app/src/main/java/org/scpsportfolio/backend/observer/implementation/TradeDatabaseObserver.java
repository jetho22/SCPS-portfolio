package org.scpsportfolio.backend.observer.implementation;

import org.scpsportfolio.backend.Trade;
import org.scpsportfolio.backend.TradeRepository;
import org.scpsportfolio.backend.observer.TradeObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeDatabaseObserver implements TradeObserver {
    Logger logger = LoggerFactory.getLogger(TradeDatabaseObserver.class);
    private final TradeRepository tradeRepository;

    public TradeDatabaseObserver(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    @Override
    public void update(Trade trade) {
        tradeRepository.save(trade);
        logger.info("Trade saved to database: {}", trade);
    }
}
