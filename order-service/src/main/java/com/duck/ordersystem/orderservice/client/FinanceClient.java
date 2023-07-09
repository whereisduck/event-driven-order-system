package com.duck.ordersystem.orderservice.client;

import com.duck.ordersystem.orderservice.model.Order;
import com.duck.ordersystem.orderservice.util.kafka.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duck.ordersystem.orderservice.client.event.OrderPaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FinanceClient {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendOrderPaymentEvent(Order orderPaymentEvent) {
        log.info("sending order payment event for order {} by customer {}", orderPaymentEvent.getId(), orderPaymentEvent.getCustomerId());
        var key = String.format("%s-shipment-dispatch", orderPaymentEvent.getId());
        kafkaTemplate.send(KafkaConfig.FINANCE_PAYMENT_PROCESS_TOPIC, key, orderPaymentEvent);
        log.info("sent out order payment event for order {} by customer {}", orderPaymentEvent.getId(), orderPaymentEvent.getCustomerId());

    }
}
