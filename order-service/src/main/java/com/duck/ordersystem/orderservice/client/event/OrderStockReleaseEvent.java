package com.duck.ordersystem.orderservice.client.event;

import com.duck.ordersystem.orderservice.model.ItemLine;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderStockReleaseEvent {
    String orderId;
    String customerId;
    List<ItemLine> itemLineList;
}
