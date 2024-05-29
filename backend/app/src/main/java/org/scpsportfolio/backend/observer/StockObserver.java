package org.scpsportfolio.backend.observer;

import org.scpsportfolio.backend.model.Stock;

public interface StockObserver {
    void update(Stock stock);
}
