package com.duck.ordersystem.financeservice.util;

public class PaymentProcessingError extends RuntimeException {
  public PaymentProcessingError(String orderId) {
    super(String.format("error processing payment for order %s", orderId));
  }
}
