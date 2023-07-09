package com.duck.ordersystem.financeservice.client;

import com.duck.ordersystem.financeservice.client.event.OrderCancellationEvent;
import com.duck.ordersystem.financeservice.model.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceClientTest {
    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks
    NotificationServiceClient notificationServiceClient;
    String orderId="order-1";
    String customerId = "customer-1";
    @Captor
    ArgumentCaptor<Invoice>  orderConfirmCaptor;
    @Captor
    ArgumentCaptor<OrderCancellationEvent> orderCancellationEventArgumentCaptor;
    @Test
    void  sendPaymentSuccessEventTest(){
        var invoice = InvoiceBuilder.get().build();
        notificationServiceClient.sendPaymentSuccessEvent(invoice);
        verify(kafkaTemplate).send( "notification.order.paid", "order-1-notification-service", invoice);

        //verify(kafkaTemplate).send(ArgumentMatchers.eq("order.payment.confirm"), ArgumentMatchers.eq("order-1-notification-service"), invoice);
        /*
        verify(kafkaTemplate).send(ArgumentMatchers.eq("order.payment.confirm"), ArgumentMatchers.eq("order-1-notification-service"), orderConfirmCaptor.capture());
        var sentEvent = orderConfirmCaptor.getValue();

        assertThat(sentEvent.getOrderId()).isEqualTo(orderId);
        assertThat(sentEvent.getCustomerId()).isEqualTo(customerId);
        assertThat(sentEvent.getBillingAddress()).isEqualTo(invoice.getBillingAddress());

         */



    }
    @Test
    void sendOrderCancellationEventTest(){
        var error = "error-message";
        var orderCancellationEvent = new OrderCancellationEvent(orderId, customerId, error);
        notificationServiceClient.sendOrderCancellationEvent(orderCancellationEvent);
        //verify(kafkaTemplate).send("notification.order.cancelled", "order-1-notification-service", orderCancellationEvent);


        verify(kafkaTemplate).send(ArgumentMatchers.eq("notification.order.cancelled"), ArgumentMatchers.eq("order-1-notification-service"), orderCancellationEventArgumentCaptor.capture());
        var sentEvent = orderCancellationEventArgumentCaptor.getValue();
        assertThat(sentEvent.getOrderId()).isEqualTo(orderId);
        assertThat(sentEvent.getCustomerId()).isEqualTo(customerId);
        assertThat(sentEvent.getMessage()).isEqualTo(error);


    }





}
