package com.duck.ordersystem.warehouseservice.listener.event;

import com.duck.ordersystem.warehouseservice.model.ItemLine;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class OrderPlacedStockCheckedEvent {
    String orderId;
    String customerId;
    List<ItemLine> itemLineList;
}
