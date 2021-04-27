package com.stock.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stock.serializer.LocalDateTimeDeserializer;
import com.stock.serializer.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Stock implements Comparable{
    private String name;
    private BigDecimal price;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", dateTime=" + dateTime.toEpochSecond(ZoneOffset.UTC) +
                ", change='" + change + '\'' +
                '}';
    }

    private String change;

    public Stock(String name, BigDecimal price, LocalDateTime dateTime) {
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
    }

    public Stock(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.dateTime = LocalDateTime.now();
    }

    public Stock(String name, String price) {
        this.name = name;
        this.price = new BigDecimal(price);
        this.dateTime = LocalDateTime.now();
    }

    public Stock() { }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public boolean isPriceSame (String price) {
        return this.getPrice().compareTo(new BigDecimal(price)) == 0;
    }

    @Override
    public int compareTo(Object o) {
        Stock that = (Stock)o;
        return this.getPrice().compareTo(that.getPrice());
    }

    public BigDecimal getDelta (Stock that) {
        BigDecimal delta = this.getPrice().subtract(that.getPrice()).abs();
        return delta.divide(this.getPrice(), 3, RoundingMode.HALF_EVEN);
    }
}
