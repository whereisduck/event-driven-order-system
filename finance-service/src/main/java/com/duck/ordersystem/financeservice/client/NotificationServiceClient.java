package com.duck.ordersystem.financeservice.client;

import com.duck.ordersystem.financeservice.client.event.OrderCancellationEvent;
import com.duck.ordersystem.financeservice.model.Invoice;
import com.duck.ordersystem.financeservice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.duck.ordersystem.financeservice.util.kafka.KafkaConfig.NOTIFICATION_ORDER_CANCELLED_TOPIC;
import static com.duck.ordersystem.financeservice.util.kafka.KafkaConfig.NOTIFICATION_ORDER_PAYED_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationServiceClient {
    private final KafkaTemplate<String, Object>  kafkaTemplate;
    public void sendOrderCancellationEvent(OrderCancellationEvent event) {
        log.info("sending order cancellation for oder{} by customer {}", event.getOrderId(), event.getCustomerId());
        var key = String.format("%s-notification-service", event.getOrderId());
        kafkaTemplate.send(NOTIFICATION_ORDER_CANCELLED_TOPIC,key, event);

        log.info("sent out order cancellation for oder{} by customer {}", event.getOrderId(), event.getCustomerId());

    }

    public void sendPaymentSuccessEvent(Invoice event) {
        log.info("sending order invoice for oder{} by customer {}", event.getOrderId(), event.getCustomerId());
        var key = String.format("%s-notification-service", event.getOrderId());
        kafkaTemplate.send(NOTIFICATION_ORDER_PAYED_TOPIC,key, event);

        log.info("sent out order invoice for oder{} by customer {}", event.getOrderId(), event.getCustomerId());


    }
}
