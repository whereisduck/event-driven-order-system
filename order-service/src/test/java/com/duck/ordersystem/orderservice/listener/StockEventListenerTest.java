package com.duck.ordersystem.orderservice.listener;

import com.duck.ordersystem.orderservice.domain.OrderBuilder;
import com.duck.ordersystem.orderservice.listener.StockEventListener;
import com.duck.ordersystem.orderservice.listener.event.StockConfirmEvent;
import com.duck.ordersystem.orderservice.model.Order;
import com.duck.ordersystem.orderservice.model.OrderStatus;
import com.duck.ordersystem.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import static com.duck.ordersystem.orderservice.model.OrderStatus.CANCELLED_RESERVED_TIME_LIMIT;
import static com.duck.ordersystem.orderservice.model.OrderStatus.RESERVED_PROCESSING_PAYMENT;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class StockEventListenerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    StockEventListener stockEventsListener;

    String orderId = "1";
    Order order = OrderBuilder.get().id(orderId).build();

    @Test
    void setOrderService(){
        assertNotNull(orderService);
    }








    @Test

    void confirmStock() throws InterruptedException {
        var stockConfirmationEvent= new StockConfirmEvent(orderId);

        when(orderService.updateStatus(any(), any())).thenReturn(Mono.just (order));
        when(orderService.findOrderById(any())).thenReturn(Mono.just(order));

        stockEventsListener.confirmStock(stockConfirmationEvent);

        verify(orderService, times(1)).updateStatus(orderId, RESERVED_PROCESSING_PAYMENT);

        verify(orderService, times(1)).sendPayment(order);



        verify(orderService, times(0)).updateStatus(orderId, CANCELLED_RESERVED_TIME_LIMIT);
        verify(orderService, times(0)).sendStockReleaseEvent(order);
    }







}

