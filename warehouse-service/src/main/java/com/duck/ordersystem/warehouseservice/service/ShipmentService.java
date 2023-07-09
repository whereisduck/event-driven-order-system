package com.duck.ordersystem.warehouseservice.service;

import com.duck.ordersystem.warehouseservice.listener.event.OrderDispatchEvent;
import com.duck.ordersystem.warehouseservice.model.Shipment;
import com.duck.ordersystem.warehouseservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    public Mono<Shipment> createShipment(OrderDispatchEvent orderDispatchEvent){
        Shipment shipment = Shipment.builder()
                .shippingAddress(orderDispatchEvent.getShippingAddress())
                .orderId(orderDispatchEvent.getOrderId())
                .customerId(orderDispatchEvent.getCustomerId())
                .dateCreated(Instant.now())
                .build();
        return shipmentRepository.save(shipment);

    }
}
