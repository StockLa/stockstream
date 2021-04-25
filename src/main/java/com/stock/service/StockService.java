package com.stock.service;

import com.stock.model.Stock;
import com.stock.source.IStockSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class StockService {

    @Autowired
    IStockSource stockSource;

    public Flux<Stock[]> getStocks(List<String> stocks) {
        return stockSource.getStocks(stocks)
                .doOnNext(System.out::println);
    }

    public Mono<Stock[]> getStock(String stock) {
        return stockSource.getStocks(List.of(stock))
                .next()
                .doOnNext(System.out::println);
    }
}
