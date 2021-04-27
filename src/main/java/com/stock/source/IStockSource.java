package com.stock.source;

import com.stock.model.Stock;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public interface IStockSource {
    Flux<Map<String,Stock>> getStocks(List<String> stocks);
}
