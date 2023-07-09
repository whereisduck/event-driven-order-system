package com.duck.ordersystem.warehouseservice.repository;


import com.duck.ordersystem.warehouseservice.model.Shipment;
import com.duck.ordersystem.warehouseservice.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ShipmentRepository extends ReactiveMongoRepository<Shipment, String> {
    //public Mono<Stock> findAllByItemIdIn(String id);
}
