package com.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.model.StockResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    StockService stockService;

//    @Test
//    void shouldBeAbleToGetStream() {
//        StepVerifier.create(stockService.getStock())
//                .expectNextCount(5)
//                .thenCancel();
//    }

//    @Test
//    void shouldBeAbleToSerialize () throws JsonProcessingException {
//        StockResponse stockResponse = new StockResponse();
//        stockResponse.setName("ibm");
//        stockResponse.setPrice("1");
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(stockResponse));
//    }
}