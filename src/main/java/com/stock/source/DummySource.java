package com.stock.source;

import com.stock.model.Stock;
import com.stock.model.StockResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

public class DummySource implements IStockSource {
    private WebClient webClient;

    public DummySource (String host) {
        webClient = WebClient.create(host);
    }

    @Override
    public Flux<Stock[]> getStocks(List<String> stocks) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/stocks")
                        .queryParam("stock", stocks)
                        .build()
                )
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(StockResponse.class)
                .map(StockResponse::getData);
    }
}
