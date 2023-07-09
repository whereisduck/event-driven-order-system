package com.duck.ordersystem.warehouseservice.util;

public class ItemNotInStock extends RuntimeException {
  public ItemNotInStock(String itemId, Integer available, Integer required) {
    super(String.format("item with id %s is not in stock. available - %d, required - %d", itemId, available, required));
  }
}
