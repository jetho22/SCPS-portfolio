package org.scpsportfolio.backend.observer;

import org.scpsportfolio.backend.Trade;

public interface TradeObserver {
    void update(Trade trade);
}
