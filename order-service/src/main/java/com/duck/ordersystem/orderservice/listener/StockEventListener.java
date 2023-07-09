package com.duck.ordersystem.orderservice.listener;

import com.duck.ordersystem.orderservice.listener.event.PaymentRejectEvent;
import com.duck.ordersystem.orderservice.listener.event.PaymentSuccessEvent;
import com.duck.ordersystem.orderservice.listener.event.StockConfirmEvent;
import com.duck.ordersystem.orderservice.listener.event.StockRejectEvent;
import com.duck.ordersystem.orderservice.model.OrderStatus;
import com.duck.ordersystem.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import zipkin2.Call;

import java.time.Duration;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.duck.ordersystem.orderservice.model.OrderStatus.*;
import static com.duck.ordersystem.orderservice.util.kafka.KafkaConfig.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventListener {
    private final OrderService orderService;
    @KafkaListener(topics=ORDER_STOCK_CONFIRM_TOPIC)
    public void confirmStock(@Payload StockConfirmEvent event ) {
        //var event = (StockConfirmEvent) stockConfirmEvent;
        String orderId = event.getOrderId();
        Long reservingTime = 3000L;
        log.info("received stock confirm event for order {}", event.getOrderId());
        orderService.updateStatus(event.getOrderId(), RESERVED_PROCESSING_PAYMENT)

                .doOnNext(orderService::sendPayment)
                //.doOnTerminate(() -> System.out.println("do after terminate"))
/*
                .doAfterTerminate(()->{
                    try {
                        log.info("entering payment pending reversing time for {}", reservingTime);
                        Thread.sleep(reservingTime);
                        log.info("past {} releasing stock reservation", reservingTime);
                        orderService.findOrderById(ld.getId())
                                .filter(l-> l.getOrderStatus() == RESERVED_PROCESSING_PAYMENT)
                                .switchIfEmpty(Mono.empty())
                                .flatMap(order -> orderService.updateStatus(order.getId(),CANCELLED_RESERVED_TIME_LIMIT))
                                .doOnNext(ls ->orderService.sendStockReleaseEvent(ls))
                                .subscribe();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    }

                )

 */
                .log()
                .subscribe();
        log.info("received stock confirm event and sent payment process event for order {}", event.getOrderId());
/*

        try {
            log.info("entering payment pending reversing time for {}", reservingTime);
            Thread.sleep(reservingTime);
            Thread.sleep(reservingTime);


            log.info("past {} releasing stock reservation", reservingTime);
            orderService.findOrderById(event.getOrderId())
                    .filter(l-> l.getOrderStatus() == RESERVED_PROCESSING_PAYMENT)
                    .switchIfEmpty(Mono.empty())
                    .flatMap(order -> orderService.updateStatus(order.getId(),CANCELLED_RESERVED_TIME_LIMIT))
                    .doOnNext(ls ->orderService.sendStockReleaseEvent(ls))
                    .subscribe();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }}

 */
        //use VirtualThread avoiding blocking thread.sleep()
        /*
        Thread.startVirtualThread(()->{
        try {
            log.info("entering payment pending reversing time for {}", reservingTime);
            Thread.sleep(reservingTime);


                log.info("past {} releasing stock reservation", reservingTime);
            orderService.findOrderById(event.getOrderId())
                    .filter(l-> l.getOrderStatus() == RESERVED_PROCESSING_PAYMENT)
                    .switchIfEmpty(Mono.empty())
                    .flatMap(order -> orderService.updateStatus(order.getId(),CANCELLED_RESERVED_TIME_LIMIT))
                    .doOnNext(ls ->orderService.sendStockReleaseEvent(ls))
                    .subscribe();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }});

         */
/*

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        log.info("entering payment pending reversing time for {}", reservingTime);

         Runnanle task1 = () ->{

            //Thread.sleep(reservingTime);

            orderService.findOrderById(orderId)
                    .filter(l -> l.getOrderStatus() == RESERVED_PROCESSING_PAYMENT)
                    .switchIfEmpty(Mono.empty())
                    .flatMap(order -> orderService.updateStatus(order.getId(), CANCELLED_RESERVED_TIME_LIMIT))
                    .doOnNext(ls -> orderService.sendStockReleaseEvent(ls))
                    .subscribe();
        };
        scheduledExecutorService.schedule(task1, 5, TimeUnit.SECONDS);

        log.info("past {} releasing stock reservation", reservingTime);
        scheduledExecutorService.shutdown();

    }




 */
        log.info("entering payment pending reversing time for {}", reservingTime);

        delay(event.getOrderId());

        log.info("past {} releasing stock reservation", reservingTime);



    }
    @Scheduled(fixedDelay = 5000L)
    private void delay(String orderId){
        log.info("past 5second releasing stock reservation");
        orderService.findOrderById(orderId)
                .filter(l -> l.getOrderStatus() == RESERVED_PROCESSING_PAYMENT)
                .switchIfEmpty(Mono.empty())
                .flatMap(order -> orderService.updateStatus(order.getId(), CANCELLED_RESERVED_TIME_LIMIT))
                .doOnNext(ls -> orderService.sendStockReleaseEvent(ls))
                .log()
                .subscribe();
        log.info("sent stock release");
    }









    @KafkaListener(topics=ORDER_STOCK_REJECT_TOPIC)
    public void rejectStock(@Payload StockRejectEvent event){
        //var event = (StockRejectEvent) stockRejectEvent;
        log.info("received stock reject event for order {}", event.getOrderId());
        orderService.updateStatus(event.getOrderId(), CANCELLED_OUT_OF_STOCK)
                //.doOnNext(orderService::sendStockReleaseEvent)
                .subscribe();
        log.info("changed order status for  stock reject event for order {}", event.getOrderId());
    }

    @KafkaListener(topics = ORDER_PAYMENT_CONFIRM_TOPIC)
    public void paymentConfirm(@Payload PaymentSuccessEvent event){
        log.info("received payment confirm event for order {}", event.getOrderId());
        orderService.updateStatus(event.getOrderId(),PAYED_PREPARING_FOR_SHIPMENT)
                //.doOnNext(orderService::sendStockReleaseEvent)
                 .doOnNext(orderService::sendOrderDispatch)
                .log()
                .subscribe();
        log.info("changed order status for  payment confirm event for order {}", event.getOrderId());
    }

    @KafkaListener(topics = ORDER_PAYMENT_REJECT_TOPIC)
    public void paymentReject(@Payload PaymentRejectEvent event){
        log.info("received payment REJECT event for order {}", event.getOrderId());
        orderService.updateStatus(event.getOrderId(),CANCELLED_PAYMENT_REJECTED)
                 .doOnNext(orderService::sendStockReleaseEvent)
                .subscribe();
        log.info("changed order status for  payment REJECT event for order {}", event.getOrderId());
    }




}
