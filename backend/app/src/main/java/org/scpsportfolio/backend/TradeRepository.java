package org.scpsportfolio.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Trade findTopBySymbolOrderByTimestampDesc(String symbol);
}
