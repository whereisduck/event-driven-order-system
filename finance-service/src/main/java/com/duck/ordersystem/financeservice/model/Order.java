package com.duck.ordersystem.financeservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
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

    public Invoice toInvoice(List<InvoiceLine> invoiceLines) {
        var totalChargeAmount = invoiceLines.stream()
                .map(tl -> tl.getPrice().multiply(BigDecimal.valueOf(tl.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Invoice.builder()
                .orderId(id)
                .customerId(customerId)
                .paymentDetails(paymentDetails)
                .billingAddress(billingAddress)
                .invoiceLines(invoiceLines)
                .dateCreated(Instant.now())
                .totalChargeAmount(totalChargeAmount)
                .build();
    }


}
