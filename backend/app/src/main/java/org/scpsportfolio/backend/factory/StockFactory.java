package org.scpsportfolio.backend.factory;

import org.scpsportfolio.backend.model.Stock;

public interface StockFactory {
    Stock createStock(String symbol, double price, long timestamp);
}
