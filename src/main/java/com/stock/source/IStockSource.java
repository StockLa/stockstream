package com.stock.source;

import com.stock.model.Stock;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IStockSource {
    Flux<Stock[]> getStocks(List<String> stocks);
}
