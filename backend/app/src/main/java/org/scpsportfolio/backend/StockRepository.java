package org.scpsportfolio.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findTopBySymbolOrderByTimestampDesc(String symbol);
}
