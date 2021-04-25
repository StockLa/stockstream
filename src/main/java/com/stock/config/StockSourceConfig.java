package com.stock.config;

import com.stock.source.DummySource;
import com.stock.source.IStockSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockSourceConfig {

    @Value("${stock.host}")
    private String host;

    @Bean
    public IStockSource dummySource () {
        return new DummySource(host);
    }
}
