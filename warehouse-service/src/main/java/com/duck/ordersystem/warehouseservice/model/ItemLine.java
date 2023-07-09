package com.duck.ordersystem.warehouseservice.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
@Value
//@Document
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ItemLine {
    String itemId;
    BigDecimal price;
    Integer quantity;
    String name;
    String description;


}

