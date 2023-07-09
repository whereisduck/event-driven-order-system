package com.duck.ordersystem.orderservice.controller;

import com.duck.ordersystem.orderservice.domain.OrderBuilder;
import com.duck.ordersystem.orderservice.dto.CreateOrderRequest;
import com.duck.ordersystem.orderservice.dto.CreateOrderResponse;
import com.duck.ordersystem.orderservice.model.Order;
import com.duck.ordersystem.orderservice.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;



import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.never;

@WebFluxTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    WebTestClient client;
    @MockBean
    OrderService orderService;
    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;
    Order testOrder = OrderBuilder.get().build();
    String orderId = UUID.randomUUID().toString();
    CreateOrderRequest orderRequest = CreateOrderRequest.builder()
            .id(orderId)
            .billingAddress(testOrder.getBillingAddress())
            .orderStatus(testOrder.getOrderStatus())
            .customerId(testOrder.getCustomerId())
            .dateCreated(testOrder.getDateCreated())
            .itemLineList(testOrder.getItemLineList())
            .paymentDetails(testOrder.getPaymentDetails())
            .shippingAddress(testOrder.getShippingAddress())
            .build();
    CreateOrderResponse orderResponse ;
    @Captor
    ArgumentCaptor<CreateOrderRequest> createOrderRequestArgumentCaptor;
    @Captor
    ArgumentCaptor<CreateOrderResponse> createOrderResponseArgumentCaptor;

    @Test
    void create(){

        //doAnswer(invocation -> Mono.just((invocation.getArguments(0)).withId(orderId)))
                //.when(orderService)
               // .createOrder(orderArgumentCaptor.capture());

        doAnswer(invocation -> Mono.just((invocation.getArgument(0))))
                .when(orderService)
                .createOrder(createOrderRequestArgumentCaptor.capture());

        client
                .post()
                .uri("/api/order")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(orderId);

        var response = createOrderRequestArgumentCaptor.getValue();
        assertThat(response.getId()).isEqualTo(orderId);






    }
    @Test
    void createWhenValidationError() {
        var createOrderRequest = CreateOrderRequest.builder().build();

        client
                .post()
                .uri("/api/order")
                .bodyValue(createOrderRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").value(Matchers.containsString("billingAddress: must not be null"))
                .jsonPath("$.message").value(Matchers.containsString("orderLines: must not be empty"))
                .jsonPath("$.message").value(Matchers.containsString("shippingAddress: must not be null"))
                .jsonPath("$.message").value(Matchers.containsString("paymentDetails: must not be null"))
                .jsonPath("$.message").value(Matchers.containsString("customerId: must not be empty"));

        verify(orderService, Mockito.never()).createOrder(any());
        verify(orderService, Mockito.never()).reserveStock(any());
    }


}
