package com.duck.ordersystem.financeservice.client.event;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PaymentSuccessEvent {
    String orderId;

}
