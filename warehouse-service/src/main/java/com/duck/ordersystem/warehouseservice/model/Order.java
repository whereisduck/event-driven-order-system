package com.duck.ordersystem.warehouseservice.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Value
@RequiredArgsConstructor
@Builder
@Document

//@NoArgsConstructor(force = true)
public class Order{
    @Id
    private final String id;
    private final List<ItemLine> itemLineList;
    @With
    OrderStatus orderStatus;
    String customerId;
    Address shippingAddress;
    Address billingAddress;
    PaymentDetails paymentDetails;
    Instant dateCreated;
    @With
    Instant dateUpdated;


}
