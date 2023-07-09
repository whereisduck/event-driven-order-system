package com.duck.ordersystem.financeservice.repository;

import com.duck.ordersystem.financeservice.model.Invoice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface InvoiceRepository extends ReactiveMongoRepository<Invoice, String> {
    public Mono<Invoice> findAllByOrderIdIn(String id);
}
