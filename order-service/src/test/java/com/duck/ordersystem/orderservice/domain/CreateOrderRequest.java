package com.duck.ordersystem.orderservice.domain;

import com.duck.ordersystem.orderservice.model.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@Value
@RequiredArgsConstructor
@Builder
public class CreateOrderRequest {
    @Id
    private final String id;
    private final List<ItemLine> itemLineList;
    OrderStatus orderStatus;
    String customerId;
    Address shippingAddress;
    Address billingAddress;
    PaymentDetails paymentDetails;
    Instant dateCreated;
    //Instant dateUpdated;

    public Order toOrder(){
        return Order.builder()
                .customerId(customerId)
                .itemLineList(itemLineList)
                .billingAddress(billingAddress)
                .shippingAddress(shippingAddress)
                .dateCreated(Instant.now())
                .orderStatus(OrderStatus.INITIATED_RESERVING_STOCK)
                .build();
    }




}
