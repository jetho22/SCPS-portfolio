package org.scpsportfolio.backend.repository;

import org.scpsportfolio.backend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findTopBySymbolOrderByTimestampDesc(String symbol);
}
