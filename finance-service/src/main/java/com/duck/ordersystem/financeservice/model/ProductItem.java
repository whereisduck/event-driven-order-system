package com.duck.ordersystem.financeservice.model;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ProductItem {
   String id;
   String itemId;
   BigDecimal price;
   String name;
   String description;
}
