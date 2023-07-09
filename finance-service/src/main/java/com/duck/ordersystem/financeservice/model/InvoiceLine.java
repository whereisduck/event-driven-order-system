package com.duck.ordersystem.financeservice.model;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class InvoiceLine {
  private final String itemId;
  private final String fullItemDescription;
  private final Integer quantity;
  private final BigDecimal price;

  public InvoiceLine(ProductItem item, int quantity) {
    this.itemId = item.getId();
    this.fullItemDescription = String.format("%s - %s", item.getName(), item.getDescription());
    this.quantity = quantity;
    this.price = item.getPrice();
  }
}
