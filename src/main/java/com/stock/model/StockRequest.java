package com.stock.model;

import com.stock.enums.ConditionType;

import java.util.HashMap;
import java.util.Map;

public class StockRequest {
    private String stockName;
    private ConditionType conditionType;
    private Map<String, String> conditions = new HashMap<>();

    @Override
    public String toString() {
        return "StockRequest{" +
                "stockName='" + stockName + '\'' +
                ", conditionType=" + conditionType +
                ", conditions=" + conditions +
                '}';
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }
}
