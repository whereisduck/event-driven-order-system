package com.duck.ordersystem.financeservice.client;

import com.duck.ordersystem.financeservice.client.event.PaymentRejectEvent;
import com.duck.ordersystem.financeservice.client.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.duck.ordersystem.financeservice.util.kafka.KafkaConfig.ORDER_PAYMENT_CONFIRM_TOPIC;
import static com.duck.ordersystem.financeservice.util.kafka.KafkaConfig.ORDER_PAYMENT_REJECT_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderServiceClient {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendPaymentSuccessEvent(PaymentSuccessEvent paymentSuccessEvent){
        log.info("sending payment success event");
        var key = String.format("%s-payment-service",paymentSuccessEvent.getOrderId() );
        kafkaTemplate.send(ORDER_PAYMENT_CONFIRM_TOPIC, key, paymentSuccessEvent);
        log.info("sent out payment success event");

    }

    public void sendPaymentRejectEvent(PaymentRejectEvent paymentRejectEvent){
        log.info("sending payment reject event");
        var key = String.format("%s-payment-service",paymentRejectEvent.getOrderId() );
        kafkaTemplate.send(ORDER_PAYMENT_REJECT_TOPIC, key, paymentRejectEvent);
        log.info("sent out payment reject event");

    }



}
