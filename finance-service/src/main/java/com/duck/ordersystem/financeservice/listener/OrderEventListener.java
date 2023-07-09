package com.duck.ordersystem.financeservice.listener;

import com.duck.ordersystem.financeservice.listener.event.OrderPaymentEvent;
import com.duck.ordersystem.financeservice.model.Order;
import com.duck.ordersystem.financeservice.service.NotificationService;
import com.duck.ordersystem.financeservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.duck.ordersystem.financeservice.util.kafka.KafkaConfig.FINANCE_PAYMENT_PROCESS_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    @KafkaListener(topics = FINANCE_PAYMENT_PROCESS_TOPIC)
    public void paymentProcess(Order order){
        log.info("received payment processing event for order {}", order.getId());
        paymentService.processPayment(order)
                .doOnError(error -> paymentService.sendPaymentRejectEvent(order.getId(), error.getMessage()))
                .doOnError(error -> notificationService.informCustomerAboutCancellation( order.getCustomerId(),order.getId(), error.getMessage()))
                .doOnSuccess(ls -> paymentService.sendPaymentConfirm(ls.getOrderId()))
                .doOnSuccess(ls->notificationService.informCustomerAboutPayment(ls))
                .subscribe();

        log.info("finished payment processing for order {}", order.getId());
    }

}
