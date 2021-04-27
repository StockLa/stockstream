package com.stock.model;

import java.util.List;

public class StockRequest {
    private List<String> stockNames = List.of();

    private long duration;

    private double delta;

    public List<String> getStockNames() {
        return stockNames;
    }

    public void setStockNames(List<String> stockNames) {
        this.stockNames = stockNames;
    }

    public long getDuration() {
        return duration;
    }

    public double getDelta() {
        return delta;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }
}
