package com.duck.ordersystem.warehouseservice.client;

import com.duck.ordersystem.warehouseservice.client.event.StockConfirmEvent;
import com.duck.ordersystem.warehouseservice.client.event.StockRejectEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.duck.ordersystem.warehouseservice.util.kafka.KafkaConfig.ORDER_STOCK_CONFIRM_TOPIC;
import static com.duck.ordersystem.warehouseservice.util.kafka.KafkaConfig.ORDER_STOCK_REJECT_TOPIC;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderServiceClient {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStockConfirm(StockConfirmEvent stockConfirmEvent){
        log.info("sending stock confirm event for order {}", stockConfirmEvent.getOrderId());
        var key = String.format("%s-stock-confirm", stockConfirmEvent.getOrderId());
        kafkaTemplate.send(ORDER_STOCK_CONFIRM_TOPIC, key, stockConfirmEvent);
        log.info("sent out stock confirm event for order {}", stockConfirmEvent.getOrderId());


    }

    public void sendStockReject(StockRejectEvent stockRejectEvent){
        log.info("sending stock reject event for order {}", stockRejectEvent.getOrderId());
        var key = String.format("%s-stock-reject", stockRejectEvent.getOrderId());
        kafkaTemplate.send(ORDER_STOCK_REJECT_TOPIC, key, stockRejectEvent);
        log.info("sent out stock confirm event for order {}", stockRejectEvent.getOrderId());

    }
}
