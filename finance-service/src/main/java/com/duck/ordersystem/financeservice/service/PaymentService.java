package com.duck.ordersystem.financeservice.service;

import com.duck.ordersystem.financeservice.client.OrderServiceClient;
import com.duck.ordersystem.financeservice.client.event.PaymentRejectEvent;
import com.duck.ordersystem.financeservice.client.event.PaymentSuccessEvent;
import com.duck.ordersystem.financeservice.model.Invoice;
import com.duck.ordersystem.financeservice.model.InvoiceLine;
import com.duck.ordersystem.financeservice.model.ItemLine;
import com.duck.ordersystem.financeservice.model.Order;
import com.duck.ordersystem.financeservice.util.PaymentProcessingError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {

    //mocking processing payment with a Rng
    private final WebClient.Builder webClient;
    private final InvoiceService invoiceService;
    private final OrderServiceClient orderServiceClient;
    private static final Random RNG = new Random();
    public Mono<Invoice> processPayment(Order order){ return processPayment(order, RNG);}

    public Mono<Invoice> processPayment(Order order, Random Rng){
        //mocking processing
              return  Flux.fromIterable(order.getItemLineList())
                .flatMap(ls -> true ? invoiceService.createInVoiceLine(ls): Mono.error(new PaymentProcessingError(order.getId())))
                .collectList().map(order::toInvoice)
                      .flatMap(invoice -> invoiceService.save(invoice));
    }

    public void sendPaymentConfirm(String orderId){
        PaymentSuccessEvent paymentSuccessEvent = new PaymentSuccessEvent(orderId);
        orderServiceClient.sendPaymentSuccessEvent(paymentSuccessEvent);

    }

    public void sendPaymentRejectEvent(String orderId, String error){
        PaymentRejectEvent paymentRejectEvent = new PaymentRejectEvent(orderId, error);
        orderServiceClient.sendPaymentRejectEvent(paymentRejectEvent);
    }




}
