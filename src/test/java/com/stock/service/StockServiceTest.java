package com.stock.service;

import com.stock.enums.ConditionType;
import com.stock.model.Stock;
import com.stock.model.StockRequest;
import com.stock.model.StockResponse;
import com.stock.source.IStockSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class StockServiceTest {

    @TestConfiguration
    static class TestSourceConfig {

        @Bean
        public IStockSource stockSource() {
            List<Map<String, Stock>> data = List.of(
                    Map.of("ibm", new Stock("ibm", "10.00"),"apple", new Stock("apple", "2.00")),
                    Map.of("ibm", new Stock("ibm", "11.00"),"apple", new Stock("apple", "8.00")),
                    Map.of("ibm", new Stock("ibm", "12.00"),"apple", new Stock("apple", "3.00")),
                    Map.of("ibm", new Stock("ibm", "20.00"),"apple", new Stock("apple", "2.00"))
                    );

            return stocks -> {
                return Flux.interval(Duration.ofSeconds(1L))
                        .map(i -> data.get(i.intValue()))
                        .take(4);
            };
        }
    }

    @Autowired
    StockService stockService;

    @Test
    void shouldBeAbleToGetStock() {
        StepVerifier.create(stockService.getStock("ibm"))
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                && stocks.get("ibm").isPriceSame("10.00"))
                .verifyComplete();
    }

    @Test
    void shouldBeAbleToGetStream() {
        StepVerifier.withVirtualTime(() -> stockService.getStocks(List.of("ibm")))
                .thenAwait(Duration.ofSeconds(4L))
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("10.00"))
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("11.00"))
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("12.00"))
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("20.00"))
                .verifyComplete();
    }

    @Test
    void shouldBeAbleToWindow () {
        StepVerifier.withVirtualTime(() -> stockService.getStocks(List.of("ibm"))
                .window(Duration.ofSeconds((2)))
                .concatMap(Flux::collectList))
                .thenAwait(Duration.ofSeconds(4L))
                .expectNextMatches(maps -> maps.get(0).get("ibm").isPriceSame("10.00") &&
                maps.get(0).get("ibm").isPriceSame("11.00"))
                .expectNextMatches(maps -> maps.get(0).get("ibm").isPriceSame("11.00") &&
                        maps.get(0).get("ibm").isPriceSame("12.00"))
                .expectNextMatches(maps -> maps.get(0).get("ibm").isPriceSame("12.00") &&
                        maps.get(0).get("ibm").isPriceSame("20.00"))
                .expectNextMatches(maps -> maps.get(0).get("ibm").isPriceSame("20.00"))
                .expectComplete();

        }

    @Test
    void shouldBeAbleToGetWithConditionAndFirstPrice () {
        StockRequest stockRequest1 = new StockRequest();
        stockRequest1.setStockName("ibm");
        stockRequest1.setConditionType(ConditionType.DELTA_LARGER_THAN);
        stockRequest1.getConditions().put("delta", "0.2");
        stockRequest1.getConditions().put("duration", "2");

        StockRequest stockRequest2 = new StockRequest();
        stockRequest2.setStockName("apple");
        stockRequest2.setConditionType(ConditionType.DELTA_LARGER_THAN);
        stockRequest2.getConditions().put("delta", "0.2");
        stockRequest2.getConditions().put("duration", "2");



        StepVerifier.withVirtualTime(() -> stockService.getStocksWithCondition(List.of(stockRequest1, stockRequest2)))
                .thenAwait(Duration.ofSeconds(4L))
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("10.00")
                        && stocks.get("apple").getName().equals("apple")
                        && stocks.get("apple").isPriceSame("2.00")
                )
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("10.00")
                        && stocks.get("apple").getName().equals("apple")
                        && stocks.get("apple").isPriceSame("8.00")
                )
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("10.00")
                        && stocks.get("apple").getName().equals("apple")
                        && stocks.get("apple").isPriceSame("3.00")
                )
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                    && stocks.get("ibm").isPriceSame("20.00")
                    && stocks.get("apple").getName().equals("apple")
                    && stocks.get("apple").isPriceSame("3.00")
                )
                .expectNextMatches(stocks -> stocks.get("ibm").getName().equals("ibm")
                        && stocks.get("ibm").isPriceSame("20.00")
                        && stocks.get("apple").getName().equals("apple")
                        && stocks.get("apple").isPriceSame("2.00")
                )
                .verifyComplete();
    }

}