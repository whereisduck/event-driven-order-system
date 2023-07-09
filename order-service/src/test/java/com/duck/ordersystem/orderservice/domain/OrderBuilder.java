package com.duck.ordersystem.orderservice.domain;

import com.duck.ordersystem.orderservice.model.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.duck.ordersystem.orderservice.model.OrderStatus.INITIATED_RESERVING_STOCK;
import static com.duck.ordersystem.orderservice.model.OrderStatus.RESERVED_PROCESSING_PAYMENT;


public class OrderBuilder {


  public static Order.OrderBuilder get() {
    var address = Address.builder()
        .line1("line1")
        .line2("line2")
        .city("city")
        .country("country")
        .county("county")
        .postcode("postcode")
        .build();
    var paymentDetails = PaymentDetails.builder()
        .nameOnCard("Joan Merkley")
        .cardType("Visa")
        .cardNumber("4038838805770816")
        .cvv(197)
        .expires("07/2023")
        .build();
    return Order.builder()
        .id("order-1")
        .customerId("customer-1")
        .itemLineList(List.of(new ItemLine("item-1",  new BigDecimal(1), Integer.valueOf(1),"item-1", "item-1")))
            .shippingAddress(address)
        .dateCreated(Instant.now())
            .dateUpdated(null)
        .paymentDetails(paymentDetails)
        .orderStatus(INITIATED_RESERVING_STOCK)
        .billingAddress(address);
  }
}
