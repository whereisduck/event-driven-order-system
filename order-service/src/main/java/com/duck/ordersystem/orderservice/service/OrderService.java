package com.duck.ordersystem.orderservice.service;


import com.duck.ordersystem.orderservice.client.event.OrderDispatchEvent;
import com.duck.ordersystem.orderservice.client.event.OrderPlacedStockCheckedEvent;
import com.duck.ordersystem.orderservice.client.event.OrderStockReleaseEvent;
import com.duck.ordersystem.orderservice.listener.event.StockRejectEvent;
import com.duck.ordersystem.orderservice.model.Order;
import com.duck.ordersystem.orderservice.util.OrderNoFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duck.ordersystem.orderservice.client.FinanceClient;
import com.duck.ordersystem.orderservice.client.WarehouseClient;
import com.duck.ordersystem.orderservice.client.event.OrderPaymentEvent;
import com.duck.ordersystem.orderservice.dto.CreateOrderRequest;
import com.duck.ordersystem.orderservice.dto.CreateOrderResponse;
import com.duck.ordersystem.orderservice.model.OrderStatus;
import com.duck.ordersystem.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WarehouseClient warehouseClient;
    private final FinanceClient financeClient;

    public Mono<Order> findOrderById(String id){
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNoFound(id)));
    }
    public Flux<Order> findAllOrders(){
        return orderRepository.findAll();
    }
    public Mono<CreateOrderResponse> createOrder (CreateOrderRequest request){

        return orderRepository.save(request.toOrder())
                .doOnNext(this::reserveStock)
                .map(Order::getId)
                .map(CreateOrderResponse::new);
    }


    public void reserveStock(Order order) {
        OrderPlacedStockCheckedEvent OrderPlacedCheckStockEvent = OrderPlacedStockCheckedEvent.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .itemLineList(order.getItemLineList())
                .build();
        warehouseClient.sendStockReservationEvent(OrderPlacedCheckStockEvent );
    }

    public Mono<Order> updateStatus(String orderId, OrderStatus status) {
                return orderRepository.findById(orderId)
                        //.switchIfEmpty(Mono.error(new OrderNoFound(orderId)))
                        .map(order -> order.withOrderStatus(status).withDateUpdated(Instant.now()))
                        //.doOnNext(orderRepository::save) ;//can't save. ????
                        .flatMap(orderRepository::save);

    }
    public void sendPayment(Order order){

        financeClient.sendOrderPaymentEvent(order);
    }
    public void sendStockReleaseEvent (Order order){
        OrderStockReleaseEvent orderStockReleaseEvent = OrderStockReleaseEvent.builder()
                .itemLineList(order.getItemLineList())
                .customerId(order.getCustomerId())
                .orderId(order.getId())
                .build();
        warehouseClient.sendStockReleaseEvent(orderStockReleaseEvent);
    }
    public void sendOrderDispatch(Order order){
        OrderDispatchEvent orderDispatchEvent = OrderDispatchEvent.builder()
                .shippingAddress(order.getShippingAddress())
                .customerId(order.getCustomerId())
                .orderId(order.getId())
                .build();
        warehouseClient.sendOrderDispatchEvent(orderDispatchEvent);

    }
}
