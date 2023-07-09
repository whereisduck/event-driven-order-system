package com.duck.ordersystem.orderservice.repository;

import com.duck.ordersystem.orderservice.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
