package com.stock.service;

import com.stock.model.Stock;
import com.stock.model.StockRequest;
import com.stock.source.IStockSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    IStockSource stockSource;

    public Flux<Map<String, Stock>> getStocks(List<String> stocks) {
        Flux<Map<String, Stock>> flux;
        flux = stockSource.getStocks(stocks);
        return flux;
    }

    public Mono<Map<String, Stock>> getStock(String stock) {
        Mono<Map<String, Stock>> mono;
        mono = stockSource.getStocks(List.of(stock))
                .next()
                .doOnNext(System.out::println);
        return mono;
    }

    public Flux<Map<String, Stock>> watchStockDelta(StockRequest stockRequests) {
        Flux<Map<String, Stock>> stocksSteam = getStocks(stockRequests.getStockNames());
        long duration = stockRequests.getDuration();
        BigDecimal delta = BigDecimal.valueOf(stockRequests.getDelta());
        List<Flux<Stock>> listStream = stockRequests.getStockNames().stream().map(stockName -> {
            Flux<Stock> stockStream = stocksSteam.map(stockSteam -> stockSteam.get(stockName));
            return Flux.merge(stockStream.take(1), stockStream
                    .window(Duration.ofSeconds(duration), Duration.ofSeconds(1))
                    .concatMap(Flux::collectList)
                    .filter(stocks -> {
                        Stock current = stocks.get(stocks.size() - 1);
                        for (Stock stock : stocks) {
                            if (current.getDelta(stock).compareTo(delta) > 0) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .map(stocks -> stocks.get(stocks.size() - 1)));
        }).collect(Collectors.toList());

        return Flux.combineLatest(listStream, objects -> {
            Map<String, Stock> result = new HashMap<>();
            for (Object object : objects) {
                Stock temp = (Stock) object;
                result.put(temp.getName(), temp);
            }
            return result;
        });
    }
}
