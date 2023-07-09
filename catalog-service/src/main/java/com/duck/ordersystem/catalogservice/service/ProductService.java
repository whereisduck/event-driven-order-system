package com.duck.ordersystem.catalogservice.service;

import com.duck.ordersystem.catalogservice.model.Product;
import com.duck.ordersystem.catalogservice.repository.ProductRepository;
import com.duck.ordersystem.catalogservice.util.ItemNoFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    /*
    public Mono<Product> getProductById (String id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNoFound(id)));
    }

     */
    public Flux<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Mono<Product> getProductByItemId(String id) {
        return productRepository.findAllByItemIdIn(id);
    }
}
