package org.scpsportfolio.backend.factory.implementation;

import org.scpsportfolio.backend.factory.StockFactory;
import org.scpsportfolio.backend.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockFactoryImpl implements StockFactory {

    @Override
    public Stock createStock(String symbol, double price, long timestamp) {
        return new Stock.Builder()
                .symbol(symbol)
                .price(price)
                .timestamp(timestamp)
                .build();
    }
}
