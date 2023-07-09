package com.duck.ordersystem.orderservice.client;

import com.duck.ordersystem.orderservice.client.event.OrderPlacedStockCheckedEvent;
import com.duck.ordersystem.orderservice.client.event.OrderStockReleaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duck.ordersystem.orderservice.client.event.OrderDispatchEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.duck.ordersystem.orderservice.util.kafka.KafkaConfig.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class WarehouseClient {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendStockReservationEvent(OrderPlacedStockCheckedEvent orderPlacedStockCheckedEvent){
        log.info("sending stock reservation for order {} by customer {}", orderPlacedStockCheckedEvent.getOrderId(), orderPlacedStockCheckedEvent.getCustomerId());
        var key = String.format("%s-stock-reservation", orderPlacedStockCheckedEvent.getOrderId());
        kafkaTemplate.send(WAREHOUSE_STOCK_RESERVE_TOPIC, key, orderPlacedStockCheckedEvent);
        log.info("sent out stock reservation for order {} by customer {}", orderPlacedStockCheckedEvent.getOrderId(), orderPlacedStockCheckedEvent.getCustomerId());
    }

    public void sendOrderDispatchEvent (OrderDispatchEvent orderDispatchEvent){
        log.info("sending order dispatch event for order {} by customer {}", orderDispatchEvent.getOrderId(), orderDispatchEvent.getCustomerId());
        var key = String.format("%s-shipment-dispatch", orderDispatchEvent.getOrderId());
        kafkaTemplate.send(WAREHOUSE_SHIPMENT_DISPATCH_TOPIC, key, orderDispatchEvent);
        log.info("sent out order dispatch event for order {} by customer {}", orderDispatchEvent.getOrderId(), orderDispatchEvent.getCustomerId());


    }

    public void sendStockReleaseEvent(OrderStockReleaseEvent orderStockReleaseEvent){
        log.info("sending stock release event for order {} by customer {}", orderStockReleaseEvent.getOrderId(), orderStockReleaseEvent.getCustomerId());
        var key = String.format("%s-stock-release", orderStockReleaseEvent.getOrderId());
        kafkaTemplate.send(WAREHOUSE_STOCK_RELEASE_TOPIC, key, orderStockReleaseEvent);
        log.info("sent out stock release event for order {} by customer {}", orderStockReleaseEvent.getOrderId(), orderStockReleaseEvent.getCustomerId());


    }




}
