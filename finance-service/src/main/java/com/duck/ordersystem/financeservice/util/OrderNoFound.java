package com.duck.ordersystem.financeservice.util;


import static org.springframework.http.HttpStatus.NOT_FOUND;

public class OrderNoFound extends ServiceApiException {


    public OrderNoFound(String id) {
        super(NOT_FOUND, String.format("Order with id %s does not exist", id));
    }
}
