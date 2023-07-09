package com.duck.ordersystem.warehouseservice.repository;


import com.duck.ordersystem.warehouseservice.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StockRepository extends ReactiveMongoRepository<Stock, String> {
    public Mono<Stock> findAllByItemIdIn(String id);
}
