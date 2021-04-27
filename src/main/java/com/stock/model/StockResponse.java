package com.stock.model;

import java.util.Map;

public class StockResponse {
    private Map<String, Stock> data;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Stock> getData() {
        return data;
    }

    public void setData(Map<String, Stock> data) {
        this.data = data;
    }
}
