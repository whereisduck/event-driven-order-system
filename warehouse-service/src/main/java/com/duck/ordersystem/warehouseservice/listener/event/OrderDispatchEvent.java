package com.duck.ordersystem.warehouseservice.listener.event;

import com.duck.ordersystem.warehouseservice.model.Address;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class OrderDispatchEvent {
    String customerId;
    String orderId;
    Address shippingAddress;

}
