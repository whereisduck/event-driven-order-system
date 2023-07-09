package com.duck.ordersystem.warehouseservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Value
@Getter
@RequiredArgsConstructor
public class Stock {
    @Id
    String id;
    String itemId;
    Integer amountAvailable;
    Integer amountReserved;

    public Stock reserveItem (Integer quantity){
        return new Stock(id, itemId, amountAvailable-quantity, amountReserved+quantity);

    }
    public Stock clearItem (Integer quantity){
        return new Stock(id, itemId, amountAvailable+quantity, amountReserved-quantity);

    }

}
