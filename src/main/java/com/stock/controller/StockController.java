package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.model.Stock;
import com.stock.model.StockRequest;
import com.stock.model.StockSubscriptionRequestDto;
import com.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public Flux<String> getStock (@RequestParam(name = "stock") List<String> stocks) {
        ObjectMapper objectMapper = new ObjectMapper();
        return stockService.getStocks(stocks)
                .map(stockResponse -> {
                    try {
                        return objectMapper.writeValueAsString(stockResponse);
                    } catch (JsonProcessingException e) {
                        logger.error(e.getMessage());
                    }
                    return "";
                }).onErrorStop();
    }

    @GetMapping("/{stock}")
    public Mono<String> getStock (@PathVariable(name = "stock") String stock) {
        ObjectMapper objectMapper = new ObjectMapper();
        return stockService.getStock(stock)
                .map(stockResponse -> {
                    try {
                        return objectMapper.writeValueAsString(stockResponse);
                    } catch (JsonProcessingException e) {
                        logger.error(e.getMessage());
                    }
                    return "";
                }).onErrorStop();
    }

    @PostMapping("/")
    public Mono<Stock[]> getStocks(@RequestBody StockRequest[] stockRequests) {
        for (StockRequest stockRequest : stockRequests) {
            System.out.println(stockRequest);
        }
       return Mono.empty();
    }
}
