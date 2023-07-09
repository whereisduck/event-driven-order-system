package com.duck.ordersystem.catalogservice.repository;

import com.duck.ordersystem.catalogservice.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<Product> findAllByItemIdIn(String id);
}
