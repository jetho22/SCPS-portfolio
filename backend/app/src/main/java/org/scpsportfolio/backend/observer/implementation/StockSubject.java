package org.scpsportfolio.backend.observer.implementation;

import org.scpsportfolio.backend.Stock;
import org.scpsportfolio.backend.observer.StockObserver;

import java.util.ArrayList;
import java.util.List;

public class StockSubject {
    private final List<StockObserver> observers = new ArrayList<>();

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Stock stock) {
        for (StockObserver observer : observers) {
            observer.update(stock);
        }
    }
}
