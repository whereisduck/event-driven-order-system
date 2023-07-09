package com.duck.ordersystem.catalogservice.util;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ItemNoFound extends ServiceApiException {
    public ItemNoFound(String itemId)  {super(NOT_FOUND, String.format("item with id %s does not exist", itemId));
    }
}
