package com.duck.ordersystem.warehouseservice.client.event;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class StockRejectEvent {
    String orderId;
    String customerId;
    //String quantity;
    String message;
}
