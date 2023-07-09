package com.duck.ordersystem.warehouseservice.util;


import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ItemNoFound extends ServiceApiException {


    public ItemNoFound(String id) {
        super(NOT_FOUND, String.format("Order with id %s does not exist", id));
    }
}
