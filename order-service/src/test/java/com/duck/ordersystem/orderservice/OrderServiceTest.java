package com.duck.ordersystem.orderservice;

import com.duck.ordersystem.orderservice.client.FinanceClient;
import com.duck.ordersystem.orderservice.client.WarehouseClient;
import com.duck.ordersystem.orderservice.client.event.OrderDispatchEvent;
import com.duck.ordersystem.orderservice.client.event.OrderPaymentEvent;
import com.duck.ordersystem.orderservice.client.event.OrderPlacedStockCheckedEvent;
import com.duck.ordersystem.orderservice.client.event.OrderStockReleaseEvent;
import com.duck.ordersystem.orderservice.domain.OrderBuilder;
import com.duck.ordersystem.orderservice.dto.CreateOrderRequest;
import com.duck.ordersystem.orderservice.dto.CreateOrderResponse;
import com.duck.ordersystem.orderservice.model.Order;
import com.duck.ordersystem.orderservice.model.OrderStatus;
import com.duck.ordersystem.orderservice.repository.OrderRepository;
import com.duck.ordersystem.orderservice.service.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static com.duck.ordersystem.orderservice.model.OrderStatus.RESERVED_PROCESSING_PAYMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock
  OrderRepository orderRepository;

  @Mock
  WarehouseClient warehouseServiceClient;

  @Mock
  FinanceClient financeServiceClient;

  @InjectMocks
  OrderService orderService;

  @Captor
  ArgumentCaptor<Order> orderArgumentCaptor;

  @Captor
  ArgumentCaptor<CreateOrderResponse> responseArgumentCaptor;


  String orderId = "order-1";
  Order order = OrderBuilder.get().id(orderId).build();


  OrderPlacedStockCheckedEvent orderPlacedStockCheckedEvent = OrderPlacedStockCheckedEvent.builder()
          .customerId(order.getCustomerId())
          .itemLineList(order.getItemLineList())
          .orderId(orderId)
          .build();

  CreateOrderRequest orderRequest = CreateOrderRequest.builder()
          .orderStatus(order.getOrderStatus())
          .shippingAddress(order.getShippingAddress())
          .billingAddress(order.getBillingAddress())
          .paymentDetails(order.getPaymentDetails())
          .customerId(order.getCustomerId())
          .dateCreated(order.getDateCreated())
          //.id(order.getId())
          .itemLineList(order.getItemLineList())
          .build();

  OrderPaymentEvent orderPaymentEvent = OrderPaymentEvent.builder()
          .paymentDetails(order.getPaymentDetails())
          .customerId(order.getCustomerId())
          .orderId(orderId)
          .build();
  OrderStockReleaseEvent orderStockReleaseEvent = OrderStockReleaseEvent.builder()
          .orderId(orderId)
          .customerId(order.getCustomerId())
          .itemLineList(order.getItemLineList())
          .build();
  OrderDispatchEvent orderDispatchEvent = OrderDispatchEvent.builder()
          .orderId(orderId)
          .shippingAddress(order.getShippingAddress())
          .customerId(order.getCustomerId())
          .build();
  CreateOrderResponse orderResponse ;

  @Test
  void create(){
    //when(orderRepository.save(any()).thenReturn(order));
    doAnswer(invocation -> Mono.just(invocation.getArgument(0))).when(orderRepository).save(any());


    var savedOrder = orderService.createOrder(orderRequest);


    StepVerifier
            .create(savedOrder)
            .expectNextMatches(order -> order.getId()!=null )
            .verifyComplete();

    verify(orderRepository).save(orderRequest.toOrder());




    }

  @Test
  void createReturnsError() {
    when(orderRepository.save(any())).thenReturn(Mono.error(RuntimeException::new));

    var savedOrder = orderService.createOrder(orderRequest);

    StepVerifier
        .create(savedOrder)
        .verifyError();

    verify(orderRepository).save(any());
  }

  @Test
  void reserveStock() {
    orderService.reserveStock(order);

    verify(warehouseServiceClient).sendStockReservationEvent(orderPlacedStockCheckedEvent);
  }


  @Test
  void updateStatus() {
    when(orderRepository.findById(orderId)).thenReturn(Mono.just(order.withOrderStatus(OrderStatus.INITIATED_RESERVING_STOCK)));
    doAnswer(invocation -> Mono.just(invocation.getArgument(0))).when(orderRepository).save(any());
    var upOrder = orderService.updateStatus(orderId, RESERVED_PROCESSING_PAYMENT);

    StepVerifier
        .create(upOrder)

            //.expectNext(order)
          .expectNextMatches(o -> o.getId().equals(orderId) && o.getOrderStatus() == RESERVED_PROCESSING_PAYMENT && o.getDateUpdated() != null)
            //.consumeNextWith(orderRepository::save)//&& o.getDateUpdated() != null)
         .verifyComplete();








    verify(orderRepository).findById(orderId);


    verify(orderRepository).save(any());

    //verify(orderRepository).save(orderArgumentCaptor.capture());
    //var updatedOrder = orderArgumentCaptor.getValue();
    //assertThat(updatedOrder.getId()).isEqualTo(orderId);
    //assertThat(updatedOrder.getDateUpdated()).isBetween(Instant.now().minusSeconds(30), Instant.now().plusSeconds(30));


  }

  @Test
  void processPayment() {
    orderService.sendPayment(order);

    verify(financeServiceClient).sendOrderPaymentEvent(order);
  }

  @Test
  void releaseStock() {
    orderService.sendStockReleaseEvent(order);

    verify(warehouseServiceClient).sendStockReleaseEvent(orderStockReleaseEvent);
  }

  @Test
  void dispatch() {
    orderService.sendOrderDispatch(order);

    verify(warehouseServiceClient).sendOrderDispatchEvent(orderDispatchEvent);
  }

}