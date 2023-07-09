package com.duck.ordersystem.orderservice.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@RequiredArgsConstructor
public class CreateOrderResponse {
    @With
    String id;

}
