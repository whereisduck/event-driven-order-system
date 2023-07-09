package com.duck.ordersystem.warehouseservice.controller;

import com.duck.ordersystem.warehouseservice.model.ItemLine;
import com.duck.ordersystem.warehouseservice.model.Stock;
import com.duck.ordersystem.warehouseservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouse")
public class WarehouseController {
    private final StockService stockService;
    @GetMapping
    public Flux<Stock> getAllStock(){
        return stockService.getAllStock();
    }

    @PutMapping
    public void updateStock(@RequestBody List<ItemLine> itemLine){
                  Flux.fromIterable(itemLine)
                .flatMap(stockService::stockReservation)
                         .subscribe();

    }


}
