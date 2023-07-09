package com.duck.ordersystem.orderservice.controller;


import com.duck.ordersystem.orderservice.model.Order;
import com.duck.ordersystem.orderservice.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duck.ordersystem.orderservice.dto.CreateOrderRequest;
import com.duck.ordersystem.orderservice.dto.CreateOrderResponse;
import com.duck.ordersystem.orderservice.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/{id}")
    public Mono<Order> findOrderById(@PathVariable String id){
        return orderService.findOrderById(id);
    }
    @GetMapping()
    public Flux<Order> getAllOrders(){
        return orderService.findAllOrders();
    }
    @PatchMapping("/{id}")
    public void changeOrderStatus(@RequestBody OrderStatus orderStatus, @PathVariable("id") String id){
        orderService.updateStatus(id, orderStatus).subscribe();

    }

    //@PutMapping("/{id}")
    //public void changeOrderStatus(@RequestBody OrderStatus orderStatus, String id){
        //orderService.updateStatus(id, orderStatus);

    //}
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<CreateOrderResponse> createOrder(@RequestBody @Validated CreateOrderRequest createOrderRequest){
        log.info("initiate order request from customer{}", createOrderRequest.getCustomerId());
        return orderService.createOrder(createOrderRequest);

    }
}
