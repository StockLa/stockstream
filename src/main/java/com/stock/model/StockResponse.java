package com.stock.model;

public class StockResponse {
    private Stock[] data;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Stock[] getData() {
        return data;
    }

    public void setData(Stock[] data) {
        this.data = data;
    }
}
