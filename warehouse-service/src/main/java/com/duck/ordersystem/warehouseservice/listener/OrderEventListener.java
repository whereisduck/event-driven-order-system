package com.duck.ordersystem.warehouseservice.listener;

import com.duck.ordersystem.warehouseservice.listener.event.OrderDispatchEvent;
import com.duck.ordersystem.warehouseservice.listener.event.OrderPlacedStockCheckedEvent;
import com.duck.ordersystem.warehouseservice.listener.event.OrderStockReleaseEvent;
import com.duck.ordersystem.warehouseservice.service.ShipmentService;
import com.duck.ordersystem.warehouseservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Timer;

import static com.duck.ordersystem.warehouseservice.util.kafka.KafkaConfig.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    private final StockService stockService;
    private final ShipmentService shipmentService;
    @KafkaListener(topics=WAREHOUSE_STOCK_RESERVE_TOPIC)
    public void reserveStock(@Payload OrderPlacedStockCheckedEvent event){
        //var event = (OrderPlacedStockCheckedEvent) orderStockCheckEvent;
        var orderId = event.getOrderId();
        log.info("received stock reservation event for order {}",orderId );
        Flux.fromIterable(event.getItemLineList())
                .flatMap(stockService::checkStock)
                .thenMany(Flux.fromIterable(event.getItemLineList()))
                //TODO .doOnNext() cant save it to database???
                .flatMap(stockService::stockReservation)
                .onErrorStop()
                .doOnError(error ->stockService.sendStockReject(orderId, event.getCustomerId(),error.getMessage()))
                .doOnComplete(()->stockService.sentStockConfirmEvent(orderId))
                .subscribe();
        log.info("sent stock checked events for order {}",orderId );
    }

    @KafkaListener(topics = WAREHOUSE_STOCK_RELEASE_TOPIC)
    public void releaseStock(@Payload OrderStockReleaseEvent event) {
        //var event = (OrderStockReleaseEvent) orderStockReleaseEvent;
        log.info("received stock release event for order {}", event.getOrderId());
        Flux.fromIterable(event.getItemLineList())
                .flatMap(stockService::clearStockReservation)
                .subscribe();
    }

    @KafkaListener(topics = WAREHOUSE_SHIPMENT_DISPATCH_TOPIC)
    public void orderDispatch(@Payload OrderDispatchEvent event) {
        //var event = (OrderStockReleaseEvent) orderStockReleaseEvent;
        log.info("received order dispatch event for order {}", event.getOrderId());
        shipmentService.createShipment(event)
                .subscribe();
        log.info("create shipment for order dispatch event of order {}", event.getOrderId());

    }

}
