package com.duck.ordersystem.financeservice.client;

import com.duck.ordersystem.financeservice.model.Address;
import com.duck.ordersystem.financeservice.model.Invoice;
import com.duck.ordersystem.financeservice.model.InvoiceLine;
import com.duck.ordersystem.financeservice.model.PaymentDetails;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceBuilder {

  public static Invoice.InvoiceBuilder get() {
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
    return Invoice.builder()
        .orderId("order-1")
        .customerId("customer-1")
        .billingAddress(address)
        .paymentDetails(paymentDetails)
        .invoiceLines(List.of(new InvoiceLine("item-1", "item - description", 3, BigDecimal.ONE)));
  }
}
