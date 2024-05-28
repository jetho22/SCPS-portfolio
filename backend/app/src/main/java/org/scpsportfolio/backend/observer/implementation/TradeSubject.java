package org.scpsportfolio.backend.observer.implementation;

import org.scpsportfolio.backend.Trade;
import org.scpsportfolio.backend.observer.TradeObserver;

import java.util.ArrayList;
import java.util.List;

public class TradeSubject {
    private final List<TradeObserver> observers = new ArrayList<>();

    public void addObserver(TradeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TradeObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Trade trade) {
        for (TradeObserver observer : observers) {
            observer.update(trade);
        }
    }
}
