package com.duck.ordersystem.orderservice.listener.event;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PaymentSuccessEvent {
    String orderId;

}
