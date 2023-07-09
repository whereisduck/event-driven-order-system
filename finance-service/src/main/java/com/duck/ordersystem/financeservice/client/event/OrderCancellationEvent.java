package com.duck.ordersystem.financeservice.client.event;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
@NoArgsConstructor(force = true)
public class OrderCancellationEvent {
  private final String orderId;
  private final String customerId;
  private final String message;
}
