package com.duck.ordersystem.catalogservice.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Value
@Document
@Builder
@RequiredArgsConstructor
public class Product {
    @Id
    String id;
    String itemId;
    BigDecimal price;
    String name;
    String description;


}
