package com.duck.ordersystem.catalogservice.controller;

import com.duck.ordersystem.catalogservice.model.Product;
import com.duck.ordersystem.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catalog")
public class ProductController {
    private final ProductService productService;
    /*
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<Product> getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

     */

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<Product> getProductByItemId(@PathVariable String id){
        return productService.getProductByItemId(id);
    }
    @GetMapping
    public Flux<Product> getAllProduct(){
        return productService.getAllProduct();
    }



}
