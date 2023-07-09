package com.duck.ordersystem.financeservice.listener.event;

import com.duck.ordersystem.financeservice.model.PaymentDetails;
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
