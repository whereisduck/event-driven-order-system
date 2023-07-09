package com.duck.ordersystem.orderservice.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Value
//@Document
@Builder
@RequiredArgsConstructor
public class ItemLine {
    String itemId;
    BigDecimal price;
    Integer quantity;
    String name;
    String description;


}

