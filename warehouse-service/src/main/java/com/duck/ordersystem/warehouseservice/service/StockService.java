package com.duck.ordersystem.warehouseservice.service;

import com.duck.ordersystem.warehouseservice.client.OrderServiceClient;
import com.duck.ordersystem.warehouseservice.client.event.StockConfirmEvent;
import com.duck.ordersystem.warehouseservice.client.event.StockRejectEvent;
import com.duck.ordersystem.warehouseservice.model.ItemLine;
import com.duck.ordersystem.warehouseservice.model.Stock;
import com.duck.ordersystem.warehouseservice.repository.StockRepository;
import com.duck.ordersystem.warehouseservice.util.ItemNoFound;
import com.duck.ordersystem.warehouseservice.util.ItemNotInStock;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final OrderServiceClient orderServiceClient;

    public Flux<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    public Mono<Void> checkStock(ItemLine itemLine) {
        var id = itemLine.getItemId();
        var quantity = itemLine.getQuantity();
        return stockRepository.findAllByItemIdIn(id)
                .switchIfEmpty(Mono.error(new ItemNoFound(id)))
                .flatMap(ls -> ls.getAmountAvailable() >= quantity ? Mono.empty() : Mono.error(new ItemNotInStock(id, ls.getAmountAvailable(), quantity)));

    }

    public Mono<Stock> stockReservation(ItemLine itemLine) {
        var id = itemLine.getItemId();
        var quantity = itemLine.getQuantity();
        return stockRepository.findAllByItemIdIn(id)
                .map(ls -> ls.reserveItem(quantity))
                .flatMap(stockRepository::save);

    }

    public void sentStockConfirmEvent(String orderId) {
        StockConfirmEvent stockConfirmEvent = new StockConfirmEvent(orderId);
        orderServiceClient.sendStockConfirm(stockConfirmEvent);
    }

    public void sendStockReject(String orderId, String customerId, String message) {
        StockRejectEvent stockRejectEvent = StockRejectEvent.builder()
                .orderId(orderId)
                //.quantity(quantity)
                .customerId(customerId)
                .message(message)
                .build();
        orderServiceClient.sendStockReject(stockRejectEvent);


    }

    public Mono<Stock> clearStockReservation(ItemLine itemLine) {
        var id = itemLine.getItemId();
        var quantity = itemLine.getQuantity();
        return stockRepository.findAllByItemIdIn(id)
                .map(ls -> ls.clearItem(quantity))
                .flatMap(stockRepository::save);

    }


}