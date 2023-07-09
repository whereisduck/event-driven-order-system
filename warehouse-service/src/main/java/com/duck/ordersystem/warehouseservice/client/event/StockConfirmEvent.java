package com.duck.ordersystem.warehouseservice.client.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class StockConfirmEvent {
    String orderId;
}
