package org.scpsportfolio.backend.repository;

import org.scpsportfolio.backend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findTopBySymbolOrderByTimestampDesc(String symbol);
}
