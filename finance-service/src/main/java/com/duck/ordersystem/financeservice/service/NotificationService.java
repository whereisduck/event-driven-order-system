package com.duck.ordersystem.financeservice.service;

import com.duck.ordersystem.financeservice.client.NotificationServiceClient;
import com.duck.ordersystem.financeservice.client.event.OrderCancellationEvent;
import com.duck.ordersystem.financeservice.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final NotificationServiceClient notificationServiceClient;

  public void informCustomerAboutCancellation(String customerId, String orderId, String message) {

    OrderCancellationEvent orderCancellationEvent = OrderCancellationEvent.builder()
            .customerId(customerId)
            .message(message)
            .orderId(orderId)
            .build();
    notificationServiceClient.sendOrderCancellationEvent(orderCancellationEvent);
  }

  public void informCustomerAboutPayment(Invoice invoice) {
    notificationServiceClient.sendPaymentSuccessEvent(invoice);
  }
}
