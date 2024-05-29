package org.scpsportfolio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private double price;
    private long timestamp;

    private Stock(Builder builder) {
        this.symbol = builder.symbol;
        this.price = builder.price;
        this.timestamp = builder.timestamp;
    }

    // default no-args constructor required by Hibernate
    public Stock() {
    }

    public static class Builder {
        private String symbol;
        private double price;
        private long timestamp;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

