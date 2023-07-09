package com.duck.ordersystem.orderservice.client.event;

import com.duck.ordersystem.orderservice.model.PaymentDetails;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder

@NoArgsConstructor(force = true)
public class OrderPaymentEvent {
    String orderId;
    String customerId;
    PaymentDetails paymentDetails;


}
