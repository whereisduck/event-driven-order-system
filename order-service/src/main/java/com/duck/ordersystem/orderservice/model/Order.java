package com.duck.ordersystem.orderservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Value
@RequiredArgsConstructor
@Builder
@Document
@With
@NoArgsConstructor(force = true)
public class Order{
    @Id
    private final String id;
    private final List<ItemLine> itemLineList;

    OrderStatus orderStatus;
    String customerId;
    Address shippingAddress;
    Address billingAddress;
    PaymentDetails paymentDetails;
    Instant dateCreated;
    Instant dateUpdated;


}
