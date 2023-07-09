package com.duck.ordersystem.orderservice.client.event;

import lombok.Builder;
import lombok.Value;
import com.duck.ordersystem.orderservice.model.ItemLine;

import java.util.List;

@Value
@Builder
public class OrderPlacedStockCheckedEvent {
    String orderId;
    String customerId;
    List<ItemLine> itemLineList;
}
