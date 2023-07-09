package com.duck.ordersystem.orderservice.client.event;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import com.duck.ordersystem.orderservice.model.Address;

@Value
@Builder
@RequiredArgsConstructor
public class OrderDispatchEvent {
    String customerId;
    String orderId;
    Address shippingAddress;

}
