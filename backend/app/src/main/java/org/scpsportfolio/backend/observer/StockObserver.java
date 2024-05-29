package org.scpsportfolio.backend.observer;

import org.scpsportfolio.backend.Stock;

public interface StockObserver {
    void update(Stock stock);
}
